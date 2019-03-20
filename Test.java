/**
 * 
 */
package main;

//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.Test;
//import org.junit.After;
//import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.After;
//import org.junit.Before;
/**
 * @author stalk
 *
 */
class Test {
    
	@org.junit.jupiter.api.Test
	
	//первый тест без капитализации, с первым калькулятором
	//вводе известен
	//ожидается вывод 100 229 руб в проценты
	//1 100 229 всего
	//ожидаемые числа взяты как мода (самое часто встречающееся значение) из онлайн-калькуляторов
	void base_test1() {
		Deposit depo = new Deposit();
    	depo.deposit = 1000000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 10;
    	depo.term = 365;
    	depo.proc = 0;
    	depo.capitaliz = false;
		depo.calculate();
		assertEquals((int)depo.finalproc, 100229);//преобразование до int из за погрешностей
		assertEquals((int)depo.deposit, 1100229);
	}
	
	@org.junit.jupiter.api.Test
	
	//аналогично, только теперь с капитализацией
	void base_test2() {
		Deposit depo = new Deposit();
    	depo.deposit = 1000000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 10;
    	depo.term = 365;
    	depo.proc = 0;
    	depo.capitaliz = true;
		depo.calculate();
		assertEquals((int)depo.finalproc, 104963);
		assertEquals((int)depo.deposit, 1104963);
	}
	
	@org.junit.jupiter.api.Test

	//аналогично, только теперь с капитализацией по периодам
	void base_test3() {
		Deposit depo = new Deposit();
    	depo.deposit = 1000000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 10;
    	depo.term = 365;
    	depo.proc = 1;
    	depo.capitaliz = true;
		depo.calculate();
		assertEquals((int)depo.finalproc, 104107);
		assertEquals((int)depo.deposit, 1104107);
	}
	
	@org.junit.jupiter.api.Test

	//с другой суммой и процентной ставкой
	void base2_test1() {
		Deposit depo = new Deposit();
    	depo.deposit = 2000000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 8;
    	depo.term = 365;
    	depo.proc = 0;
    	depo.capitaliz = false;
		depo.calculate();
		assertEquals((int)depo.finalproc, 160366);
		assertEquals((int)depo.deposit, 2160366);
	}
	
	@org.junit.jupiter.api.Test

	//с другой суммой и процентной ставкой и сроком
	void base3_test1() {
		Deposit depo = new Deposit();
    	depo.deposit = 500000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 5;
    	depo.term = 60;
    	depo.proc = 0;
    	depo.capitaliz = false;
		depo.calculate();
		assertEquals((int)depo.finalproc, 4178);
		assertEquals((int)depo.deposit, 504178);
	}
	
	@org.junit.jupiter.api.Test

	//с другой суммой и процентной ставкой и сроком и капитализацией
	void base4_test1() {
		Deposit depo = new Deposit();
    	depo.deposit = 400000;
    	depo.day = 17;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 7;
    	depo.term = 91;
    	depo.proc = 0;
    	depo.capitaliz = true;
		depo.calculate();
		assertEquals((int)depo.finalproc, 7099);
		assertEquals((int)depo.deposit, 407099);
	}
	
	@org.junit.jupiter.api.Test
	
	//с другой суммой и процентной ставкой и сроком
		void base5_test1() {
			Deposit depo = new Deposit();
	    	depo.deposit = 300000;
	    	depo.day = 20;
	    	depo.month = 3;
	    	depo.year = 2019;
	    	depo.interest = 8;
	    	depo.term = 365;
	    	depo.proc = 0;
	    	depo.capitaliz = false;
			depo.calculate();
			assertEquals((int)depo.finalproc, 24054);
			assertEquals((int)depo.deposit, 324054);
		}
	
	@org.junit.jupiter.api.Test
	
	//тест без капитализации, ожидается вывод проценты - 8 018, всего - 108 018
	void base6_test1() {
		Deposit depo = new Deposit();
    	depo.deposit = 100000;
    	depo.day = 20;
    	depo.month = 3;
    	depo.year = 2019;
    	depo.interest = 8;
    	depo.term = 365;
    	depo.proc = 0;
    	depo.capitaliz = false;
		depo.calculate();
		assertEquals((int)depo.finalproc, 8018);
		assertEquals((int)depo.deposit, 108018);
	}

}

