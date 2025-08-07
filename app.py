
from flask import Flask, request, render_template, url_for
import jinja2
from deepface import DeepFace
import cv2  # 이미지 처리 (필요 시)
import os

app = Flask(__name__)

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

            # 이미지 URL 생성
            image_url = url_for('static', filename=f'uploads/{filename}')

            return render_template('result.html', emotion=dominant_emotion, scores=emotion_scores, image_url=image_url)

    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)