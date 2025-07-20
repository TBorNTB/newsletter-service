#!/bin/bash

# 배포 스크립트
set -e

# 환경 변수 설정
AWS_REGION=${AWS_REGION:-ap-northeast-2}
ECR_REPOSITORY=${ECR_REPOSITORY:-newsletter-service}
AWS_ACCOUNT_ID=${AWS_ACCOUNT_ID}
CONTAINER_NAME=${CONTAINER_NAME:-newsletter-service}
PORT=${PORT:-8080}

# 색상 정의
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 로그 함수
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 필수 환경 변수 확인
if [ -z "$AWS_ACCOUNT_ID" ]; then
    log_error "AWS_ACCOUNT_ID 환경 변수가 설정되지 않았습니다."
    exit 1
fi

# ECR 레지스트리 URL
ECR_REGISTRY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
IMAGE_URI="${ECR_REGISTRY}/${ECR_REPOSITORY}:latest"

log_info "배포를 시작합니다..."
log_info "ECR 레지스트리: ${ECR_REGISTRY}"
log_info "이미지 URI: ${IMAGE_URI}"

# AWS CLI 설치 확인
if ! command -v aws &> /dev/null; then
    log_warn "AWS CLI가 설치되지 않았습니다. 설치를 시작합니다..."
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
    unzip awscliv2.zip
    sudo ./aws/install
    rm -rf awscliv2.zip aws/
fi

# Docker 설치 확인
if ! command -v docker &> /dev/null; then
    log_warn "Docker가 설치되지 않았습니다. 설치를 시작합니다..."
    sudo apt-get update
    sudo apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io
    sudo usermod -aG docker $USER
    log_info "Docker 설치가 완료되었습니다. 시스템을 재시작하거나 로그아웃 후 다시 로그인하세요."
fi

# 기존 컨테이너 중지 및 제거
log_info "기존 컨테이너를 중지하고 제거합니다..."
sudo docker stop ${CONTAINER_NAME} 2>/dev/null || true
sudo docker rm ${CONTAINER_NAME} 2>/dev/null || true

# ECR 로그인
log_info "ECR에 로그인합니다..."
aws ecr get-login-password --region ${AWS_REGION} | sudo docker login --username AWS --password-stdin ${ECR_REGISTRY}

# 최신 이미지 풀
log_info "최신 이미지를 가져옵니다..."
sudo docker pull ${IMAGE_URI}

# 새 컨테이너 실행
log_info "새 컨테이너를 실행합니다..."
sudo docker run -d \
    --name ${CONTAINER_NAME} \
    --restart unless-stopped \
    -p ${PORT}:8080 \
    -e SPRING_PROFILES_ACTIVE=prod \
    -e SPRING_DATASOURCE_URL=${DATABASE_URL} \
    -e SPRING_DATASOURCE_USERNAME=${DATABASE_USERNAME} \
    -e SPRING_DATASOURCE_PASSWORD=${DATABASE_PASSWORD} \
    -e SPRING_REDIS_HOST=${REDIS_HOST} \
    -e SPRING_REDIS_PORT=${REDIS_PORT} \
    -e SPRING_REDIS_PASSWORD=${REDIS_PASSWORD} \
    -e SPRING_MAIL_HOST=${MAIL_HOST} \
    -e SPRING_MAIL_PORT=${MAIL_PORT} \
    -e SPRING_MAIL_USERNAME=${MAIL_USERNAME} \
    -e SPRING_MAIL_PASSWORD=${MAIL_PASSWORD} \
    -e SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true \
    -e SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true \
    ${IMAGE_URI}

# 컨테이너 상태 확인
log_info "컨테이너 상태를 확인합니다..."
sleep 5
sudo docker ps

# 로그 확인
log_info "컨테이너 로그를 확인합니다..."
sudo docker logs ${CONTAINER_NAME} --tail 20

log_info "배포가 완료되었습니다!"
log_info "애플리케이션은 http://localhost:${PORT} 에서 접근할 수 있습니다." 