import cv2

# カメラのキャプチャを初期化。
capture = cv2.VideoCapture(0)

#iPhoneカメラのキャプチャを初期化。
#capture = cv2.VideoCapture(1)

# macOSでは cv2.VideoCapture(0) で問題なく動作するかもしれません。
# Windowsで遅延が発生するか、カメラがオンにならない場合は、cv2.VideoCapture(0, cv2.CAP_DSHOW)を試してください。

# フレームを表示するための名前付きウィンドウを作成。
cv2.namedWindow('frame')

# カメラからフレームをリアルタイムで読み取るメインループ
while True:
    # フレームを1つずつキャプチャ。
    ret, frame = capture.read()

    if not ret:
        print("フレームの取得に失敗しました")
        break

    # フレームにガウシアンブラーを適用。
    blur = cv2.GaussianBlur(frame, (5, 5), 0)

    # ブラーをかけたフレームにラプラシアンフィルタを適用。
    laplacian = cv2.Laplacian(blur, cv2.CV_64F)

    # 'frame'という名前のウィンドウにオリジナルのフレームを表示。
    #cv2.imshow('frame', frame)

    # 'Laplacian'という名前のウィンドウにラプラシアンフィルターを適用したイメージを表示。
    cv2.imshow('Laplacian', laplacian)

    # 'q'キーが押されたら終了。
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# すべてが終わったら、キャプチャを解放してウィンドウを閉じる。
capture.release()
cv2.destroyAllWindows()
