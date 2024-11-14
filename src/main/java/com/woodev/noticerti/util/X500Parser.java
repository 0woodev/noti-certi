package com.woodev.noticerti.util;


import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * X500Parser
 * X.500 Distinguished Name(DN) 을 파싱하는 클래스<p>
 * DN 은 다음과 같은 형식을 가진다.
 * <pre>
 *     CN=Common Name, OU=Organizational Unit, O=Organization, L=Locality, S=State, C=Country
 *     - CN : Common Name (사용자 이름)
 *     - OU : Organizational Unit (부서)
 *     - O : Organization (회사)
 *     - L : Locality (지역)
 *     - S : State (주)
 *     - C : Country (국가)
 *     - DN : Distinguished Name (고유 이름) [ex. CN=Common Name, OU=Organizational Unit, O=Organization, L=Locality, S=State, C=Country]
 *     - RDN : Relative Distinguished Name (상대 고유 이름) [ex. key=value]
 * </pre>
 * DN 을 파싱하여 각각의 요소를 추출할 수 있다.
 * <p>
 * 용도 : X.500 구조를 기반으로 하는 X.509 인증서의 DN 을 파싱하는 클래스
 * @see <a href="https://en.wikipedia.org/wiki/Distinguished_Name">Distinguished Name</a>
 */
public class X500Parser {

    /**
     * X500 구조의 문자열에서 특정 필드의 값을 반환한다. (없는 경우 null 반환)
     *
     * @param dn X500 구조의 문자열
     * @param key 필드 명
     * @return String 타입의 필드 값
     */
    public static String getValue(String dn, String key) {
        try {
            LdapName ldapName = new LdapName(dn);
            return ldapName.getRdns().stream()
                    .filter(rdn -> rdn.getType().equalsIgnoreCase(key))
                    .findFirst()
                    .map(rdn -> rdn.getValue().toString())
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * X500 형식의 문자열을 파싱하여 Map&lt;String, String&gt; 으로 반환한다.
     *
     * @param dn X500 구조의 문자열
     * @return 파싱된 Map
     */
    public static Map<String, Object> parse(String dn) {
        try {
            LdapName ldapName = new LdapName(dn);
            return ldapName.getRdns()
                    .stream()
                    .collect(Collectors.toMap(Rdn::getType, Rdn::getValue));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
