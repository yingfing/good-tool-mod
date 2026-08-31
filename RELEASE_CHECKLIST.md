# 项目发布检查清单

## ✅ 已完成的任务

### 1. 项目配置
- [x] Minecraft 26.2 Fabric 环境
- [x] Minecraft 1.21.11 Fabric 环境
- [x] 使用 Mojang 官方映射表
- [x] Java 25 支持
- [x] Fabric Loom 现代配置

### 2. 功能实现
- [x] 暂停菜单服务器信息按钮
- [x] 服务器信息网络包系统
- [x] 多人游戏信息显示屏幕
- [x] Mod Menu 集成与配置界面
- [x] 完整的错误处理和 null 检查

### 3. GitHub Actions
- [x] 构建 workflow (build.yml)
  - 支持 Minecraft 26.2 版本构建
  - 支持 Minecraft 1.21.11 版本构建
  - 自动上传构建产物
  
- [x] 发布 workflow (release.yml)
  - 监听 tag 推送 (v* 格式)
  - 自动构建 JAR
  - 自动创建 GitHub Release
  - 自动上传 JAR 文件到 Release

### 4. 文档
- [x] AI Agent 说明 (.github/copilot-instructions.md)
- [x] Gradle 故障排查指南 (GRADLE_TROUBLESHOOTING.md)
- [x] Gradle 清理脚本 (gradle-cleanup.sh)

## 📊 当前发布状态

| 项目 | 版本 | 状态 | JAR 位置 |
|------|------|------|--------|
| Minecraft 1.21.11 | 1.0.0-beta.1 | ✅ 已构建 | `minecraft-fabric-project/build/libs/` |
| Minecraft 26.2 | 1.0.0-beta.1 | ✅ 已构建 | `build/libs/` |
| GitHub Release | v1.0.0-beta.1 | ⏳ 进行中 | https://github.com/yingfing/good-tool-mod/releases/tag/v1.0.0-beta.1 |

## 🚀 GitHub Actions 工作流

### 构建流程 (build.yml)
触发条件: `push` 或 `pull_request`

```
代码推送/PR创建
    ↓
验证 Gradle wrapper
    ↓
安装 JDK 25 (Microsoft)
    ↓
构建 Minecraft 26.2 版本 ──┐
                          ├→ 上传构建产物 (artifacts)
构建 Minecraft 1.21.11 版本 ──┘
    ↓
完成 ✅
```

### 发布流程 (release.yml)
触发条件: 推送标签匹配 `v*` 格式

```
推送标签 (如 v1.0.0-beta.1)
    ↓
验证 Gradle wrapper
    ↓
安装 JDK 25 (Microsoft)
    ↓
构建 Minecraft 1.21.11 JAR
    ↓
下载构建产物
    ↓
创建 GitHub Release (自动生成版本说明)
    ↓
上传 JAR 文件到 Release
    ↓
完成 ✅
```

## 📋 如何创建新的发布版本

### 1. 更新版本号
编辑 `gradle.properties` 和 `minecraft-fabric-project/gradle.properties`:
```properties
version=1.0.0  # 改为你想要的版本
```

### 2. 提交更改
```bash
git add .
git commit -m "Release version 1.0.0"
git push origin 26.2
```

### 3. 创建发布标签
```bash
git tag v1.0.0
git push origin v1.0.0
```

### 4. 查看构建进度
打开: https://github.com/yingfing/good-tool-mod/actions

点击最新的 "Release Build and Publish" workflow 查看进度。

### 5. 完成后检查 Release
构建完成后，Release 会自动发布到:
https://github.com/yingfing/good-tool-mod/releases/tag/v1.0.0

## 🔧 本地构建（如果遇到 Gradle 问题）

### 快速清理
```bash
chmod +x gradle-cleanup.sh
./gradle-cleanup.sh
```

### 手动构建 1.21.11
```bash
cd minecraft-fabric-project
./gradlew clean build
# 输出: build/libs/modid-1.0.0-beta.1.jar
```

### 手动构建 26.2
```bash
cd ..
./gradlew clean build
# 输出: build/libs/modid-1.0.0-beta.1.jar
```

## 📞 故障排查

如果 GitHub Actions 构建失败：

1. 查看 Actions 页面的详细错误日志
2. 参考 `GRADLE_TROUBLESHOOTING.md`
3. 本地尝试构建验证代码有效性
4. 检查 Java 版本和 Gradle 版本兼容性

## 🎯 下一步

1. **测试构建**
   - [ ] 在本地测试构建完的 JAR
   - [ ] 验证 Mod 功能正常
   - [ ] 检查服务器信息显示

2. **改进功能**
   - [ ] 添加更多服务器信息字段
   - [ ] 实现服务器定时刷新
   - [ ] 支持显示 TPS/MSPT

3. **发布**
   - [ ] 标记稳定版本 v1.0.0
   - [ ] 更新 README 说明
   - [ ] 提交到 Modrinth/CurseForge

## 📄 相关文件

- `.github/workflows/build.yml` - 构建工作流
- `.github/workflows/release.yml` - 发布工作流
- `.github/copilot-instructions.md` - AI 代理说明
- `GRADLE_TROUBLESHOOTING.md` - Gradle 故障排查
- `gradle-cleanup.sh` - Gradle 清理脚本

---

**最后更新**: 2026-08-31
**最新版本**: v1.0.0-beta.1
