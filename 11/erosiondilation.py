import cv2
import numpy as np

#縮小
def erosion(img):
    #４近傍が0の場合に0に、それ以外は1にする
    h, w = img.shape
    out = np.zeros((h, w), dtype=float)
    for y in range(1, h-1):
        for x in range(1, w-1):
            if img[y-1, x] == 0 or img[y, x-1] == 0 or img[y, x+1] == 0 or img[y+1, x] == 0:
                out[y, x] = 0
            else:
                out[y, x] = 1
    return out

#膨張
def dilation(img):
    #４近傍が1の場合に1に、それ以外は0にする
    h, w = img.shape
    out = np.zeros((h, w), dtype=float)
    for y in range(1, h-1):
        for x in range(1, w-1):
            if img[y-1, x] == 1 or img[y, x-1] == 1 or img[y, x+1] == 1 or img[y+1, x] == 1:
                out[y, x] = 1
            else:
                out[y, x] = 0
    return out


# 画像を読み込む
img = cv2.imread("sakurajima.jpeg")

#画像の２値化
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

#閾値を設定
thresh = 100

#閾値を超えた画素を1にする
gray[gray < thresh] = 0
gray[gray >= thresh] = 1

#膨張縮小処理
out_erosion = erosion(gray)
out_dilation = dilation(gray)

#結果を出力
cv2.imwrite("gray.jpg", gray*255)
cv2.imwrite("out_erosion.jpg", out_erosion*255)
cv2.imwrite("out_dilation.jpg", out_dilation*255)
cv2.imshow("result", out_dilation)
cv2.waitKey(0)
cv2.destroyAllWindows()



