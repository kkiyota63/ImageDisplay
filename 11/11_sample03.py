
# -*- coding: utf-8 -*-

''' 

'''

import numpy as np
import cv2

  
# メイン関数
def main():
    # 画像を読み込む
    img = cv2.imread('sample01.png',0)
    rows, cols = img.shape
 
    th, bin_image = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)
    rows, cols = bin_image.shape

    kernel = np.ones((5,5),np.uint8)
    erosion = cv2.erode(bin_image,kernel,iterations = 1)
    opening = cv2.dilate(erosion,kernel,iterations = 1)
    
    cv2.namedWindow("original_image")   
    cv2.namedWindow("binary_image")   
    
    cv2.namedWindow("opening")   

    cv2.imshow("original_image", img)
    cv2.imshow("binary_image", bin_image)
    cv2.imshow("opening", opening)

    cv2.waitKey(0)
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
