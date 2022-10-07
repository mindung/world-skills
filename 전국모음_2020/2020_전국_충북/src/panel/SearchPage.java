package panel;

public class SearchPage extends BasePanel {

	public class Data {
		public String name;
		public String[] item;
		
		public Data(String name, String items) {
			this.name = name;
			this.item = items.split(", ");
		}
	}
	
	public SearchPage(String category) { 
		Data data[][] = {
				{
					new Data("브랜드", "AMD, 인텔"),
					new Data("소켓 구분", "AMD(소켓AM4), AMD(소켓TR4), 인텔(소켓1151), 인텔(소켓(1151v2), 인텔(소켓1200), 인텔(소켓2066)"),
					new Data("코드네임", "마티스, 서밋 릿지, 스카레이크, 스카이레이크, 카비레이크, 커피레이크, 커피레이크-R, 케스케이드레이크, 코멧레이크-R, 코멧레이크S, 파니클 릿지, 피카소, 화이트헤븐"),
					new Data("코어 수", "10코어, 12코어, 14코어, 듀얼 코어, 옥타(8) 코어, 옥타(8)코어, 쿼드(4)코어, 헥사(6)코어")
				},
				{
					new Data("브랜드", "ASRock, ASUS, BIOSTAR, GIGABYTE, MSI"),
					new Data("소켓 구분", "AMD(소켓AM4), 인텔(소켓1151V2), 인텔(소켓1200)"),
					new Data("세부 칩셋", "(AMD) A320, (AMD) B450, (AMD) x570, (인텔) B360, (인텔) B365, (인텔) B460, (인텔) H310, (인텔) Z490"),
					new Data("폼팩터", "ATX, M-ATX")
				},
				{
					new Data("브랜드", "ADATA, G.SKILL, GeIL, TeamGroup, 마이크론, 삼성전자, 써멀테이크"),
					new Data("용량", "16GB, 32GB, 8GB"),
					new Data("사용 장치", "PC용, 노트북용"),
					new Data("소켓 구분", "DDR4")
				},
				{
					new Data("브랜드", "APPHIRE, ASRock, ASUS, COLORFUL, GAINWARD, MSI, PALIT, ZOTAC, 갤럭시, 이엠텍"),
					new Data("칩셋", "GTC 1650, GTX 1660 SUPER, RTX 2060, RTX 2060 SUPER, RTX 2070 SUPER, RX 570, RX 5700 XT"),
					new Data("소비전력", "최대 125W, 최대 130W, 최대 175W, 최대 190W, 최대 215W, 최대 250W, 최대 255W, 최대 265W, 최대 75W"),
					new Data("메모리 용량", "4GB, 6GB, 8GB")
				},
				{
					new Data("브랜드", "ADATA, BIOSTAR, PNY, Seagate, Toshiba, Western, 마이크론, 삼성전자, 타무즈"),
					new Data("제품 타입", "내장형SSD"),
					new Data("메모리 타입", "MLC(동기식), QLC, TLC(기타), TLC(토글)"),
					new Data("용량", "120GB, 1TB, 240GB, 250GB, 256GB, 500GB, 512GB, 960GB")
				},
				{
					new Data("브랜드", "3RSYS, ABKO, Antec, COOLMAX, COX, DAVEN, Fractal, darkFlash, 대양케이스, 마이크로닉스, 아이구주, 잘만"),
					new Data("제품분류", "PC케이스(ATX), PC케이스(M-ATX)"),
					new Data("케이스 크기", "미니타워, 미들타워, 빅타워"),
					new Data("지원보드규격", "Extended-ATX, Micro-ATX, Mini-ITX, 표준-ATX, 표준-ITX")
				},
				{
					new Data("브랜드", "ABKO, Antec, COOLMAX, CORSAIR, EVGA, Enermax, FSP, 델타, 마이크로닉스, 시소닉, 잘만, 쿨러마스터"),
					new Data("제품분류", "ATX 파워, M-ATX(SFX) 파워"),
					new Data("정격출력", "1000W, 1300W, 500W, 600W, 650W, 700W, 750W"),
					new Data("인증사항", "80 PLUS 골드, 80 PLUS 브론즈, 80 PLUS 스탠다드, 80 PLUS 실버, 80 PLUS 티타늄, 80 PLUS 플레티넘")
				}
		};
		
		
		
	}
	
}
