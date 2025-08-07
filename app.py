
from flask import Flask, request, render_template, url_for
from flask_sqlalchemy import SQLAlchemy
from datetime import datetime
import jinja2
from deepface import DeepFace
import cv2  # 이미지 처리 (필요 시)
import os
import json
import numpy as np

app = Flask(__name__)

# MariaDB 설정
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:password@localhost:3306/emotion_analysis'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

# JSON 필터 추가
@app.template_filter('from_json')
def from_json_filter(value):
    try:
        return json.loads(value)
    except:
        return {}

# NumPy float32를 일반 float로 변환하는 함수
def convert_numpy_types(obj):
    if hasattr(obj, 'item'):  # NumPy scalar
        return obj.item()
    elif isinstance(obj, dict):
        return {key: convert_numpy_types(value) for key, value in obj.items()}
    elif isinstance(obj, list):
        return [convert_numpy_types(item) for item in obj]
    return obj

# 데이터베이스 모델
class EmotionResult(db.Model):
    __tablename__ = 'emotion_results'
    
    id = db.Column(db.Integer, primary_key=True)
    image_filename = db.Column(db.String(255), nullable=False)
    dominant_emotion = db.Column(db.String(50), nullable=False)
    emotion_scores = db.Column(db.Text, nullable=False)  # JSON 형태로 저장
    analysis_time = db.Column(db.DateTime, default=datetime.utcnow)
    
    def __repr__(self):
        return f'<EmotionResult {self.id}: {self.dominant_emotion}>'

# 홈페이지: 이미지 업로드 폼
@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        file = request.files['file']
        if file:
            # static 폴더에 파일 저장
            upload_folder = os.path.join(app.root_path, 'static', 'uploads')
            os.makedirs(upload_folder, exist_ok=True)
            filename = 'uploaded_image.jpg'
            file_path = os.path.join(upload_folder, filename)
            file.save(file_path)

            # DeepFace로 감정 분석
            try:
                analysis = DeepFace.analyze(img_path=file_path, actions=['emotion'])
            except ValueError as e:
                # 얼굴을 감지할 수 없는 경우 에러 페이지로 이동
                if "Face could not be detected" in str(e):
                    return render_template('error.html')
                else:
                    # 다른 ValueError의 경우 다시 발생시킴
                    raise e


            # 결과 추출 (가장 강한 감정)
            if isinstance(analysis, list):
                analysis = analysis[0]
            dominant_emotion = analysis['dominant_emotion']
            emotion_scores = analysis['emotion']
            
            # NumPy 타입을 일반 Python 타입으로 변환
            emotion_scores = convert_numpy_types(emotion_scores)

            # 이미지 URL 생성
            image_url = url_for('static', filename=f'uploads/{filename}')

            # 데이터베이스에 결과 저장 (데이터베이스 연결이 활성화된 경우에만)
            try:
                # JSON 직렬화를 위해 float32를 float로 변환
                emotion_scores_json = {}
                for key, value in emotion_scores.items():
                    if hasattr(value, 'item'):  # NumPy scalar
                        emotion_scores_json[key] = float(value.item())
                    else:
                        emotion_scores_json[key] = float(value)
                
                emotion_result = EmotionResult(
                    image_filename=filename,
                    dominant_emotion=dominant_emotion,
                    emotion_scores=json.dumps(emotion_scores_json)
                )
                db.session.add(emotion_result)
                db.session.commit()
            except Exception as e:
                print(f"Database error: {e}")
                # 데이터베이스 오류가 있어도 결과는 표시

            return render_template('result.html', emotion=dominant_emotion, scores=emotion_scores, image_url=image_url)

    return render_template('index.html')

# 분석 기록 조회 페이지
@app.route('/history')
def history():
    try:
        results = EmotionResult.query.order_by(EmotionResult.analysis_time.desc()).limit(50).all()
        return render_template('history.html', results=results)
    except Exception as e:
        print(f"Database error in history: {e}")
        return render_template('history.html', results=[])

# 데이터베이스 테이블 생성
# with app.app_context():
#     db.create_all()

if __name__ == '__main__':
    app.run(debug=True)