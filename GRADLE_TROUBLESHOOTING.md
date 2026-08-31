# Gradle 构建故障排查指南

## 问题描述

本地 Gradle 构建可能遇到以下问题：
- 下载缓慢或超时
- Gradle daemon 卡顿
- 内存不足
- 依赖解析失败

## 快速修复

### 1. 清理所有缓存
```bash
# 清理项目缓存
cd minecraft-fabric-project
rm -rf .gradle/ build/

# 清理全局 Gradle 缓存
rm -rf ~/.gradle/caches/
rm -rf ~/.gradle/notifications/
```

### 2. 重新初始化 Gradle
```bash
cd minecraft-fabric-project
chmod +x gradlew
./gradlew wrapper --gradle-version 8.5
```

### 3. 增加 JVM 内存
编辑 `gradle.properties`：
```properties
org.gradle.jvmargs=-Xmx2G -XX:MaxMetaspaceSize=512M
```

### 4. 禁用 Gradle daemon
```bash
./gradlew build --no-daemon
```

## 完整构建步骤

### Minecraft 1.21.11 版本（推荐）

```bash
cd minecraft-fabric-project

# 清理旧构建
./gradlew clean

# 下载依赖并构建
./gradlew build

# 输出位置
# build/libs/modid-1.0.0-beta.1.jar
```

### Minecraft 26.2 版本

```bash
cd ..  # 回到项目根目录

# 清理旧构建
./gradlew clean

# 下载依赖并构建
./gradlew build

# 输出位置
# build/libs/modid-1.0.0-beta.1.jar
```

## 常见错误

### 错误 1: "Could not download fabric-loom"
**原因**: 网络问题或 Maven 仓库不可用
**解决**:
```bash
# 使用代理（示例使用阿里云）
./gradlew build \
  -Dmaven.aliyun.com.releases=https://maven.aliyun.com/repository/public \
  -Dmaven.aliyun.com.snapshots=https://maven.aliyun.com/repository/snapshots
```

### 错误 2: "Out of memory"
**原因**: JVM 内存不足
**解决**:
```bash
# 增加内存
export GRADLE_OPTS="-Xmx4G"
./gradlew build
```

### 错误 3: "Connection timeout"
**原因**: 网络不稳定
**解决**:
```bash
# 增加超时时间
./gradlew build \
  -Dorg.gradle.jvmargs=-Djavax.net.debug=ssl \
  --info
```

## 使用自动清理脚本

```bash
chmod +x gradle-cleanup.sh
./gradle-cleanup.sh
```

## Docker 构建（推荐用于 CI/CD）

```bash
docker run --rm -v $(pwd):/workspace -w /workspace/minecraft-fabric-project \
  openjdk:25-slim \
  bash -c "chmod +x gradlew && ./gradlew build"
```

## 验证构建

构建完成后应该看到：
```
BUILD SUCCESSFUL in XXs
```

输出的 JAR 文件在：
- `minecraft-fabric-project/build/libs/modid-1.0.0-beta.1.jar`

## GitHub Actions 构建

推送到 GitHub 后，自动化构建会在以下场景触发：
1. **任何 push** - 运行 `build.yml` workflow
2. **创建 tag (v*)** - 运行 `release.yml` workflow，自动发布 Release

查看状态：
```
https://github.com/yingfing/good-tool-mod/actions
```

## 性能优化

### 启用构建缓存
编辑 `gradle.properties`:
```properties
org.gradle.caching=true
```

### 并行构建
```bash
./gradlew build --parallel
```

### 只构建不测试
```bash
./gradlew build -x test
```

## 获取帮助

如果以上方法都不奏效：

1. 检查日志:
```bash
./gradlew build --info --debug 2>&1 | tee build.log
```

2. 查看 Fabric 官方文档:
https://docs.fabricmc.net/develop/getting-started/creating-a-project

3. 在 Gradle 论坛寻求帮助:
https://discuss.gradle.org/
