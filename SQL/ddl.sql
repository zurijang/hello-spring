-- 회원 테이블
DROP TABLE IF EXISTS MEMBER CASCADE;
CREATE TABLE MEMBER (
                        id bigint generated by default as identity,
                        name varchar(255),
                        primary key (id)
);

-- 회원 테이블에 테스트 데이터 추가
INSERT INTO MEMBER(NAME) VALUES ('spring1');
INSERT INTO MEMBER(NAME) VALUES ('spring2');
INSERT INTO MEMBER(NAME) VALUES ('spring3');