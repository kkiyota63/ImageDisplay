import cv2
import numpy as np
import csv

# 特定のピクセルが輪郭の一部かどうかを判断する関数
def is_contour_pixel(img, y, x):
    # ピクセルが黒で、その8近傍の少なくとも1つが白い場合、輪郭の一部とみなす
    if img[y, x] == 0:
        if (img[y-1:y+2, x-1:x+2].max() == 255):
            return True
    return False

# 与えられた開始ピクセルから輪郭を追跡する関数
def track_contour(binary_img, start, visited):
    directions = [(0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1), (-1, 0), (-1, 1)]  # 移動可能な8方向
    direction_idx = 0  # 右への移動から開始
    contour = [start]
    visited.add(start)  # 開始ピクセルを訪問済みとしてマーク
    current = start

    while True:
        found_next_pixel = False
        for _ in range(len(directions)):
            dy, dx = directions[direction_idx]
            next_pixel = (current[0] + dy, current[1] + dx)
            if next_pixel not in visited and is_contour_pixel(binary_img, next_pixel[0], next_pixel[1]):
                contour.append(next_pixel)
                visited.add(next_pixel)
                current = next_pixel
                direction_idx = (direction_idx - 1) % 8  # 右に回転して輪郭に沿う
                found_next_pixel = True
                break
            else:
                direction_idx = (direction_idx + 1) % 8  # 輪郭ピクセルが見つからない場合は左に回転

        if not found_next_pixel:
            break  # 次のピクセルが見つからない場合、輪郭は完了

    return contour

# 画像中の全ての図形の輪郭を見つける関数
def find_shapes_contours(binary_img):
    contours = []
    visited = set()  # 訪問済みの輪郭ピクセルを追跡するためのセット

    for y in range(1, binary_img.shape[0] - 1):
        for x in range(1, binary_img.shape[1] - 1):
            if is_contour_pixel(binary_img, y, x) and (y, x) not in visited:
                contour = track_contour(binary_img, (y, x), visited)
                contours.append(contour)

    return contours

# 画像を読み込む
image_path = 'sample01.png'  # 正しい画像パスに置き換えてください
image = cv2.imread(image_path, cv2.IMREAD_GRAYSCALE)

# 画像が正しく読み込まれたか確認
if image is None:
    print(f"画像を読み込めませんでした: {image_path}")
    exit()

# 画像を二値化
_, binary_image = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY)

# 全ての図形の輪郭を見つける
all_contours = find_shapes_contours(binary_image)

# 輪郭の座標をcsvファイルに保存
with open('contours.csv', 'w') as f:
    writer = csv.writer(f)
    for contour in all_contours:
        writer.writerows(contour)
