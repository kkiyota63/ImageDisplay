#opencvをインポート
import cv2

print(cv2.__version__)

#画像を読み込む
img = cv2.imread("test.jpg")

#ウィンドウの名前を設定
cv2.namedWindow("image")

#画像を表示
cv2.imshow("image",img)

#キー入力を待つ
cv2.waitKey(0)

#ウィンドウを閉じる
cv2.destroyAllWindows()
