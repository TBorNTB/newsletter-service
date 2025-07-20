#!/bin/bash

# 헬스체크 스크립트
set -e

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

# 환경 변수 설정
CONTAINER_NAME=${CONTAINER_NAME:-newsletter-service}
HEALTH_CHECK_URL=${HEALTH_CHECK_URL:-http://localhost:8080/actuator/health}
TIMEOUT=${TIMEOUT:-30}

log_info "헬스체크를 시작합니다..."
log_info "컨테이너: ${CONTAINER_NAME}"
log_info "헬스체크 URL: ${HEALTH_CHECK_URL}"

# 컨테이너 상태 확인
if ! sudo docker ps | grep -q ${CONTAINER_NAME}; then
    log_error "컨테이너 ${CONTAINER_NAME}가 실행되지 않고 있습니다."
    exit 1
fi

log_info "컨테이너가 실행 중입니다."

# 애플리케이션 헬스체크
log_info "애플리케이션 헬스체크를 수행합니다..."

# curl이 설치되어 있는지 확인
if ! command -v curl &> /dev/null; then
    log_warn "curl이 설치되지 않았습니다. 설치를 시작합니다..."
    sudo apt-get update
    sudo apt-get install -y curl
fi

# 헬스체크 수행
response=$(curl -s -o /dev/null -w "%{http_code}" --max-time ${TIMEOUT} ${HEALTH_CHECK_URL} || echo "000")

if [ "$response" = "200" ]; then
    log_info "애플리케이션이 정상적으로 응답하고 있습니다. (HTTP ${response})"
    exit 0
else
    log_error "애플리케이션이 정상적으로 응답하지 않습니다. (HTTP ${response})"
    
    # 컨테이너 로그 확인
    log_info "최근 컨테이너 로그를 확인합니다..."
    sudo docker logs ${CONTAINER_NAME} --tail 20
    
    exit 1
fi 