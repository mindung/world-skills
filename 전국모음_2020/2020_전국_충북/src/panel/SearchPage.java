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
					new Data("�귣��", "AMD, ����"),
					new Data("���� ����", "AMD(����AM4), AMD(����TR4), ����(����1151), ����(����(1151v2), ����(����1200), ����(����2066)"),
					new Data("�ڵ����", "��Ƽ��, ���� ����, ��ī����ũ, ��ī�̷���ũ, ī����ũ, Ŀ�Ƿ���ũ, Ŀ�Ƿ���ũ-R, �ɽ����̵巹��ũ, �ڸ䷹��ũ-R, �ڸ䷹��ũS, �Ĵ�Ŭ ����, ��ī��, ȭ��Ʈ���"),
					new Data("�ھ� ��", "10�ھ�, 12�ھ�, 14�ھ�, ��� �ھ�, ��Ÿ(8) �ھ�, ��Ÿ(8)�ھ�, ����(4)�ھ�, ���(6)�ھ�")
				},
				{
					new Data("�귣��", "ASRock, ASUS, BIOSTAR, GIGABYTE, MSI"),
					new Data("���� ����", "AMD(����AM4), ����(����1151V2), ����(����1200)"),
					new Data("���� Ĩ��", "(AMD) A320, (AMD) B450, (AMD) x570, (����) B360, (����) B365, (����) B460, (����) H310, (����) Z490"),
					new Data("������", "ATX, M-ATX")
				},
				{
					new Data("�귣��", "ADATA, G.SKILL, GeIL, TeamGroup, ����ũ��, �Ｚ����, �������ũ"),
					new Data("�뷮", "16GB, 32GB, 8GB"),
					new Data("��� ��ġ", "PC��, ��Ʈ�Ͽ�"),
					new Data("���� ����", "DDR4")
				},
				{
					new Data("�귣��", "APPHIRE, ASRock, ASUS, COLORFUL, GAINWARD, MSI, PALIT, ZOTAC, ������, �̿���"),
					new Data("Ĩ��", "GTC 1650, GTX 1660 SUPER, RTX 2060, RTX 2060 SUPER, RTX 2070 SUPER, RX 570, RX 5700 XT"),
					new Data("�Һ�����", "�ִ� 125W, �ִ� 130W, �ִ� 175W, �ִ� 190W, �ִ� 215W, �ִ� 250W, �ִ� 255W, �ִ� 265W, �ִ� 75W"),
					new Data("�޸� �뷮", "4GB, 6GB, 8GB")
				},
				{
					new Data("�귣��", "ADATA, BIOSTAR, PNY, Seagate, Toshiba, Western, ����ũ��, �Ｚ����, Ÿ����"),
					new Data("��ǰ Ÿ��", "������SSD"),
					new Data("�޸� Ÿ��", "MLC(�����), QLC, TLC(��Ÿ), TLC(���)"),
					new Data("�뷮", "120GB, 1TB, 240GB, 250GB, 256GB, 500GB, 512GB, 960GB")
				},
				{
					new Data("�귣��", "3RSYS, ABKO, Antec, COOLMAX, COX, DAVEN, Fractal, darkFlash, ������̽�, ����ũ�δн�, ���̱���, �߸�"),
					new Data("��ǰ�з�", "PC���̽�(ATX), PC���̽�(M-ATX)"),
					new Data("���̽� ũ��", "�̴�Ÿ��, �̵�Ÿ��, ��Ÿ��"),
					new Data("��������԰�", "Extended-ATX, Micro-ATX, Mini-ITX, ǥ��-ATX, ǥ��-ITX")
				},
				{
					new Data("�귣��", "ABKO, Antec, COOLMAX, CORSAIR, EVGA, Enermax, FSP, ��Ÿ, ����ũ�δн�, �üҴ�, �߸�, �𷯸�����"),
					new Data("��ǰ�з�", "ATX �Ŀ�, M-ATX(SFX) �Ŀ�"),
					new Data("�������", "1000W, 1300W, 500W, 600W, 650W, 700W, 750W"),
					new Data("��������", "80 PLUS ���, 80 PLUS �����, 80 PLUS ���Ĵٵ�, 80 PLUS �ǹ�, 80 PLUS ƼŸ��, 80 PLUS �÷�Ƽ��")
				}
		};
		
		
		
	}
	
}
