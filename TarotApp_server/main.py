from flask import Flask, request, jsonify
from llama_cpp import Llama
import logging
import socket
from werkzeug.exceptions import ClientDisconnected

app = Flask(__name__)
logging.basicConfig(level=logging.DEBUG)

# 모델 파일은 서버 디스크의 절대 경로로 지정
model = Llama(
    model_path="D:/AndroidProject/TarotApp/TarotApp_server/EXAONE.gguf",
    n_ctx=2048,
    n_threads=4
)

@app.route("/api/infer", methods=["POST"])
def infer():
    data = request.get_json()
    prompt = data.get("prompt", "")
    if not prompt:
        return jsonify({"error": "No prompt provided"}), 400

    try:
        resp = model(prompt, max_tokens=512)
        text = resp["choices"][0].get("text", "")
        print(f">>> PROMPT: {prompt}\n>>> RESULT: {text!r}\n")  # PyCharm console에 찍히는 LLM 결과

        return jsonify({"response": text})

    except ClientDisconnected:
        app.logging.warning("클라이언트가 연결을 끊었습니다. generation 중단합니다.")
        return "", 204

    except (BrokenPipeError, ConnectionResetError, socket.error) as e:
        app.logging.warning(f"소켓 에러 발생: {e!r}, 요청 처리 취소")
        return "", 204

    except Exception as e:
        # 이 외의 모든 에러는 500으로 로깅만 남기고 서버는 살아있도록
        app.logging.exception("LLM 생성 중 예기치 못한 에러")
        return jsonify({"error": "Generation failed"}), 500

if __name__ == "__main__":
    # debug=True: 코드 변경시 자동 reload + 더 상세한 에러 로그
    app.run(host="0.0.0.0", port=5523, debug=True)