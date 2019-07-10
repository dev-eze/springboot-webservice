package com.eze.webservice.domain.post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * DB 테이블과 매칭될 Entity Class
 */

/**
 * access = AccessLevel.PROTECTED : 기본생성자의 접근 권한을 protected로 제한을 두는 것은
 * 생성자로 protected Post() {}와 같은 선상입니다.
 * Entitiy클래스를 프로젝트 코드상에서 기본 생성자로 생성하는 것을 막고,
 * JPAdptj Entity클래스를 생성하는것은 허용하기 위함입니다.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 롬복의 기본 생성자 자동추가 코드
/**
 * Entitiy클래스 생성시 무분별한 setter메소드 생성을 피해야 합니다.
 * getter/setter를 무분별하게 생성한 경우 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 명확히 구분할수가 없기 때문에
 * 차후 기능 변경, 유지보수시에 공수가 더 들어갈 수 있습니다.
 * 해당 필드의 값 변경이 필요한 경우 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 만들어서 사용하는게 좋습니다.
 */
@Getter //  클래스내 모든 필드의 Getter메소드를 자동 생성해주는 롬복 어노테이션입니다.
@Entity //  테이블과 링크될 클래스임을 나타냅니다., 언더스코어 네이밍으로 테이블 이름과 매칭합니다.
public class Post {

    @Id //  해당 테이블의 PK 필드를 나타냅니다.

    /**
     * Hibernate 5부터 MySQL에서의 GenerationType.AUTO는 IDENTITY가 아닌 TABLE을 기본 시퀀스 전략으로 가져간다는 것입니다.
     * table sequence generators가 적용된걸 알 수 있었습니다.
     * 공용 시퀀스 테이블을 두고 모든 테이블의 id 시퀀스를 한테이블에서 관리하는 방식입니다.
     * Spring Boot는 Hibernate의 id 생성 전략을 그대로 따라갈지 말지를 결정하는 useNewIdGeneratorMappings 설정이 있다.
     * 1.5에선 기본값이 false, 2.0부터는 true
     * Hibernate 5.0부터 MySQL의 AUTO는 IDENTITY가 아닌 TABLE을 기본 시퀀스 전략으로 선택된다.
     * 즉, 1.5에선 Hibernate 5를 쓰더라도 AUTO를 따라가지 않기 때문에 IDENTITY가 선택
     * 2.0에선 true이므로 Hibernate 5를 그대로 따라가기 때문에 TABLE이 선택
     *
     * application.properties/yml의 use-new-id-generator-mappings을 false로 설정한다
     * @GeneratedValue의 전략을 GenerationType.IDENTITY로 지정한다
     */

    /**
     * 왠만하면 Entity의 PK는 Long타입(Mysql 기준 BIGINT)의 auto_increment를 권장합니다.
     * 유니크키, 또는 여러키를 조합한 복합키로 PK로 선택한 경우 FK를 맺을 때 브릿지 역할을 하는 테이블을 둬야하는 상황이 발생합니다.
     * 인덱스 태우기 좋습니다.
     * 유니크한 조건이 변경된 경우, PK전체를 수정해야할 일이 발생할 수 있습니다.
     */
    @GeneratedValue(strategy = GenerationType.AUTO) //  PK의 생성 규칙을 나타냅니다. 기본은 AUTO로 Mysql의 auto_increment와 같이 자동증가하는 정수형 값을 나타냅니다.
    private Long id;

    /**
     * 지정하지 않아도 괜찮지만 굳이 선언하는 이유는 기본값 이외에 변경이 필요한 경우 사용합니다.
     * 문자열의 경우 VARCHAR(255)가 기본값인데, 이 사이즈를 변경하고 싶거나 타입을 변경하고 싶을때 사용됩니다.
     */
    @Column(name = "제목", length = 500, nullable = false)    //  테이블의 컬럼을 나타내며, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼으로 지정됩니다.
    private String title;

    @Column(name = "내용", columnDefinition = "TEXT", nullable = true)
    private String contents;

    private String author;

    @Builder    // 해당 클래스의 빌더패턴 클래스를 생성해주는 롬복 어노테이션 입니다. 생성자 상단에 선언하게되면 생성장에 포함된 필드만 빌더에 포함됩니다.
    public Post(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }
}
