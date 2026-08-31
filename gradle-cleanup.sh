#!/bin/bash

# Gradle 清理脚本 - 解决本地构建问题

echo "========================================="
echo "  Minecraft Fabric Mod - Gradle 清理"
echo "========================================="
echo ""

# 进入项目目录
cd "$(dirname "$0")/minecraft-fabric-project" || exit 1

echo "1. 清理 Gradle 缓存..."
rm -rf ~/.gradle/caches/
rm -rf .gradle/

echo "2. 清理构建输出..."
rm -rf build/
rm -rf .gradle/

echo "3. 重新下载 Gradle wrapper..."
rm -rf gradle/wrapper/
chmod +x gradlew

echo "4. 验证 Gradle wrapper..."
./gradlew wrapper --gradle-version 8.5

echo ""
echo "========================================="
echo "  清理完成！"
echo "========================================="
echo ""
echo "现在可以尝试构建:"
echo "  ./gradlew build"
echo ""
