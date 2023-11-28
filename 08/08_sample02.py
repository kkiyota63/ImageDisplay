# -*- coding: utf-8 -*-

import numpy as np
import cv2

# 二次元配列の値をグレースケール画像の帯域に変換
def convert_img( data ,rows, cols):
     data  = data.ravel()
     
     m0 = np.amin(data)
     m1 = np.amax(data)
     
     if m0 < 0:
         data = data - m0

     dd = m1 - m0
     data = 255 * data / dd

     data = data.reshape(rows, cols) 

     data = np.uint8( data )
     return data

    
# メイン関数
def main():
    # 画像をグレースケールで読み込む
    img = cv2.imread('sakurajima.jpeg',0)

    # mask_image = cv2.imread('mask_high.bmp',0)
    # mask = (mask_image/255,2)
    
    # DFT
    dft = cv2.dft(np.float32(img),flags = cv2.DFT_COMPLEX_OUTPUT)
    # 結果をシフト
    dft_shift = np.fft.fftshift(dft)

    magnitude_spectrum = 20*np.log(cv2.magnitude(dft_shift[:,:,0],dft_shift[:,:,1]))
    

    # 画像のサイズ，中心座標
    rows, cols = img.shape
    crow,ccol = int(rows/2) , int(cols/2)
    
    # ハイパスフィルタ
    mask = np.ones((rows,cols,2),np.uint8)
    mask[crow-100:crow+100, ccol-100:ccol+100] = 0
    
    # フィルタ適用
    filtered_dft_shift = dft_shift * mask         
            
    # シフトしていたのを元に戻す
    filtered_dft = np.fft.ifftshift(filtered_dft_shift)
    
    # 逆変換
    output_image = cv2.idft(filtered_dft)
    output_image = cv2.magnitude(output_image[:,:,0],output_image[:,:,1])
    
    # スケール変換
    output_image = convert_img(output_image, rows, cols)

    
    # フィルター後の画像ファイルの保存
    cv2.imwrite("magnitude_spectrum.png", magnitude_spectrum)
    cv2.imwrite("output_image_high.png", output_image)
    
    cv2.namedWindow("original_image")
    cv2.namedWindow("output_image")
    
    cv2.imshow("original_image", img)
    cv2.imshow("output_image", output_image)

    cv2.waitKey(0)
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
