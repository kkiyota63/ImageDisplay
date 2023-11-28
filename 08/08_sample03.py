
# -*- coding: utf-8 -*-

''' 
カラー画像のサンプル
'''

import cv2
    
# メイン関数
def main():
    # 画像を読み込む
    img = cv2.imread('sakurajima.jpeg')

    b_image = img.copy()
    g_image = img.copy()
    r_image = img.copy()
    
    
    # blue
    b_image[:, :, 1] = 0
    b_image[:, :, 2] = 0  

    # green
    g_image[:, :, 0] = 0
    g_image[:, :, 2] = 0

    # red
    r_image[:, :, 0] = 0
    r_image[:, :, 1] = 0


    cv2.namedWindow("original_image")
    cv2.namedWindow("R")
    cv2.namedWindow("G")
    cv2.namedWindow("B")
    
    cv2.imshow("original_image", img)
    cv2.imshow("R", r_image)
    cv2.imshow("G", g_image)
    cv2.imshow("B", b_image)
    

    cv2.waitKey(0)
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
