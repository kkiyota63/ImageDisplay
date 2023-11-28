
# -*- coding: utf-8 -*-

''' 
カラー画像のサンプル 別バージョン
'''

import cv2
    
# メイン関数
def main():
    # 画像を読み込む
    img = cv2.imread('sakurajima.jpeg')

    # BGRに分解　（色の順番に注意）
    bgr_img = cv2.split(img)

    # 合成
    merge_image = cv2.merge( (bgr_img[2], bgr_img[0], bgr_img[0]) )

    cv2.namedWindow("original_image")
    
    cv2.namedWindow("B")
    cv2.namedWindow("G")
    cv2.namedWindow("R")
    cv2.namedWindow("merge_image")    
    
    cv2.imshow("original_image", img)
    cv2.imshow("B", bgr_img[0])
    cv2.imshow("G", bgr_img[1])
    cv2.imshow("R", bgr_img[2])
    cv2.imshow("merge_image", merge_image)
    
    cv2.waitKey(0)
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
