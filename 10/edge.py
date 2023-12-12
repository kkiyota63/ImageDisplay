import cv2
import numpy as np
import pandas as pd

# 画像ファイルのパスを設定 (実際のファイルパスに書き換えてください)
image_path = "sample01.png"

# 画像を読み込む
image = cv2.imread(image_path)

# グレースケールに変換
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 二値化またはキャニーエッジ検出を適用
_, thresh = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)

# 輪郭を検出
contours, _ = cv2.findContours(thresh, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

# 輪郭の座標を抽出
contour_list = []
for contour in contours:
    for point in contour:
        x, y = point[0]
        contour_list.append([x, y])

# DataFrameに変換
df = pd.DataFrame(contour_list, columns=["x", "y"])

# CSVファイルに保存
csv_output_path = "csv_output.csv"
df.to_csv(csv_output_path, index=False)

# 出力ファイルパスを表示
print(f"CSV file saved to: {csv_output_path}")
