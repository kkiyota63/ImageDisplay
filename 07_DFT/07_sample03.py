# -*- coding: utf-8 -*-

import numpy as np
import cv2
from matplotlib import pyplot as plt

# 画像をグレースケールで読み込む
img = cv2.imread('sakurajima.jpeg',0)

# DFT
dft = cv2.dft(np.float32(img),flags = cv2.DFT_COMPLEX_OUTPUT)
# 結果をシフト
dft_shift = np.fft.fftshift(dft)

# パワースペクトル
magnitude_spectrum = 20*np.log(cv2.magnitude(dft_shift[:,:,0],dft_shift[:,:,1]))

# 画像のサイズ，中心座標
rows, cols = img.shape
crow,ccol = int(rows/2) , int(cols/2)
mask = np.zeros((rows,cols,2),np.uint8)
mask[crow-30:crow+30, ccol-30:ccol+30] = 1

# フィルタ適用
filtered_dft_shift = dft_shift*mask         
        
# シフトしていたのを元に戻す
iltered_dft = np.fft.ifftshift(filtered_dft_shift)

# 逆変換
inverse_img = cv2.idft(filtered_dft_shift)
inverse_img = cv2.magnitude(inverse_img[:,:,0],inverse_img[:,:,1])
inverse_img = cv2.rotate(inverse_img, cv2.ROTATE_180)

plt.subplot(131),plt.imshow(img, cmap = 'gray')
plt.title('Input Image'), plt.xticks([]), plt.yticks([])
plt.subplot(132),plt.imshow(magnitude_spectrum, cmap = 'gray')
plt.title('Magnitude Spectrum'), plt.xticks([]), plt.yticks([])

plt.subplot(133),plt.imshow(inverse_img, cmap = 'gray')
plt.title('Inverse Image'), plt.xticks([]), plt.yticks([])

plt.show()


