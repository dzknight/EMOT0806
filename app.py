from flask import Flask, request, render_template
import jinja2 
from deepface import DeepFace
import cv2  # 이미지 처리 (필요 시)

app = Flask(__name__)

# 홈페이지: 이미지 업로드 폼
@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        # 업로드된 파일 가져오기
        file = request.files['file']
        if file:
            # 파일 저장 (임시로 메모리에서 처리)
            file_path = 'uploaded_image.jpg'
            file.save(file_path)
            
            # DeepFace로 감정 분석
            analysis = DeepFace.analyze(img_path=file_path, actions=['emotion'])
            
            # 결과 추출 (가장 강한 감정)
            dominant_emotion = analysis[0]['dominant_emotion']
            emotion_scores = analysis[0]['emotion']
            
            # 결과 반환
            return render_template('result.html', emotion=dominant_emotion, scores=emotion_scores)
    
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)