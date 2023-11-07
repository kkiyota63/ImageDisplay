import cv2

# カメラのキャプチャを初期化します。
capture = cv2.VideoCapture(0)

# macOSでは cv2.VideoCapture(0) で問題なく動作するかもしれません。
# Windowsで遅延が発生するか、カメラがオンにならない場合は、cv2.VideoCapture(0, cv2.CAP_DSHOW)を試してください。

# フレームを表示するための名前付きウィンドウを作成します。
cv2.namedWindow('frame')

# カメラからフレームをリアルタイムで読み取るメインループ
while True:
    # フレームを1つずつキャプチャします。
    ret, frame = capture.read()

    if not ret:
        print("フレームの取得に失敗しました")
        break

    # フレームにガウシアンブラーを適用します。
    blur = cv2.GaussianBlur(frame, (5, 5), 0)

    # ブラーをかけたフレームにラプラシアンフィルタを適用します。
    laplacian = cv2.Laplacian(blur, cv2.CV_64F)

    # 'frame'という名前のウィンドウにオリジナルのフレームを表示します。
    cv2.imshow('frame', frame)

    # 'Laplacian'という名前のウィンドウにラプラシアンフィルターを適用したイメージを表示します。
    cv2.imshow('Laplacian', laplacian)

    # 'q'キーが押されたら終了します。
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# すべてが終わったら、キャプチャを解放してウィンドウを閉じます。
capture.release()
cv2.destroyAllWindows()
