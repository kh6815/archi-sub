# BATCH 서버 소개
배치 서버는 API서버를 대신하여 주기적인 데이터 처리 및 데이터 관리를 위한 시스템으로 사용되고 있습니다.

전체 소개글은 해당 프로젝트를 참고해주세요
- [API 서버](https://github.com/kh6815/archi)

## 주요 기능
- 인기 게시글 생성: 매주 정해진 요일과 시간에 게시글의 좋아요 수를 비교하여, 인기 게시글을 자동으로 업데이트합니다.
- 유저 프로필 이미지 삭제: 유저프로필과 관련된 파일 및 연관 테이블을 삭제하고, S3에 저장된 파일도 함께 제거합니다.
- 게시글 프로필 이미지 삭제: 게시글과 관련된 파일 및 연관 테이블을 삭제하고, S3에 저장된 파일도 함께 제거합니다.
- 공지사항 프로필 이미지 삭제: 공지사항과 관련된 파일 및 연관 테이블을 삭제하고, S3에 저장된 파일도 함께 제거합니다.
- 읽은 알림 삭제: 읽음처리된 알림을 주기적으로 삭제합니다.

## 프로젝트 구조
<pre>
archi-back-sub/
├── src/
│   ├── main/
│   │   ├── java/com/archi_sub.archi_sub
│   │   │   ├── common/          # 서버 공통 로직 및 에러 처리 정의
│   │   │   ├── config/          # config 파일 관리 및 배치 로직 개발
│   │   │   └── db/              # DB 관련 Entity, Repository 관리
│   │   └── resources/
│   │       ├── profiles/        # 환경별 yml 설정 분리
│   │       │   ├── dev/         # dev 환경 설정
│   │       │   ├── local/       # local 환경 설정
│   │       │   ├── prod/        # prod 환경 설정
│   │       └── application-core.yml # 공통 환경 설정 파일
└── ...
</pre>



## 기술 스택
- Java 17
- Spring Boot
- JPA (Hibernate), QueryDSL, MySQL
- AWS
  - S3
  - EC2


## ERD
- 유저 테이블
- 파일 관련 테이블
- 공지사항 관련 테이블
- 게시물 관련 테이블
- 댓글 관련 테이블

해당 테이블들의 연관관계를 표현한 다이어그램을 확인할 수 있습니다.

![archi drawio](https://github.com/user-attachments/assets/b3c45c6e-dc98-41f9-bacf-ea3589830a93)



## 배포
서버 배포는 AWS를 사용하여 진행했으며, API 서버와 같은 EC2 서버에 배포 했습니다.
- EC2 인스턴스에서 Docker를 통해 MySQL를 컨테이너 형태로 실행하여 데이터베이스 연동.
- Spring Boot 애플리케이션은 개발(dev) 환경에서 JAR 파일을 빌드한 후 EC2에 업로드하여 실행.
