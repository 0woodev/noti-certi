# Entity Annotation
## @OneToMany
* orphanRemoval (default : false)
  * true : 연관관계가 끊어진 SubjectAlternativeName 엔티티를 삭제한다.
  * false : 연관관계가 끊어진 SubjectAlternativeName 엔티티를 삭제하지 않는다.
* CascadeType (default : CascadeType.ALL)
  * ALL : Domain 엔티티를 저장할 때 SubjectAlternativeName 엔티티도 함께 저장한다.
  * PERSIST : Domain 엔티티를 저장할 때 SubjectAlternativeName 엔티티도 함께 저장한다.
  * MERGE : Domain 엔티티를 저장할 때 SubjectAlternativeName 엔티티도 함께 저장한다.
  * REMOVE : Domain 엔티티를 삭제할 때 SubjectAlternativeName 엔티티도 함께 삭제한다.
  * REFRESH : Domain 엔티티를 갱신할 때 SubjectAlternativeName 엔티티도 함께 갱신한다.
  * DETACH : Domain 엔티티를 분리할 때 SubjectAlternativeName 엔티티도 함께 분리한다.
  * REPLICATE : Domain 엔티티를 복제할 때 SubjectAlternativeName 엔티티도 함께 복제한다.
  * LOCK : Domain 엔티티를 잠금할 때 SubjectAlternativeName 엔티티도 함께 잠금한다.
* fetch (default : FetchType.LAZY)
  * FetchType.LAZY : Domain 엔티티를 조회할 때 SubjectAlternativeName 엔티티를 함께 조회하지 않는다.
  * FetchType.EAGER : Domain 엔티티를 조회할 때 SubjectAlternativeName 엔티티를 함께 조회한다.
* mappedBy (default : "")
  * Domain 엔티티에 정의된 SubjectAlternativeName 엔티티의 필드명을 지정한다.