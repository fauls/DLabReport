package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;

//
//основной класс для работы с депозитом
//
public class Deposit {
	double deposit; //сумма
	int year;
	int month;
	int day;
	int term; //срок
	double interest; //процентная ставка
	boolean capitaliz; //капитализация
	int proc =0; //0 - капитализация, 1 - через 3 месяца, 2 - ежегодно
	String[][] table; //таблица с датой, процентами и остатком
	
	private LocalDate _startdate; //начальная дата
	private LocalDate _stopdate; //конечная
	private int _periods; //кол-во месяцев (проценты добавляются помесячно)
	double finalproc=0; //итоговые проценты
	
	public void setDate(int date)//проверка дня
	{
		LocalDate startdate = LocalDate.of(year, month, 2);
		if (date<1 || date>startdate.lengthOfMonth())
		{throw new NumberFormatException();}else
		{day = date;}
	}
	
	public void calculate()
	{
		dayInPeriod();//подсчёт срока
		
		if (capitaliz)
		{hardInterest();}
		else
		{simpleInterest();}//если не капитализация, то это простые проценты
	}

	private void dayInPeriod()//считаем время от вложения вклада до забора
	{
		_startdate = LocalDate.of(year, month, day); //конвертируем дату из формата ввода в удобный
		_stopdate = _startdate; 
		_stopdate = _stopdate.plusDays(term+1);//начальная дата + срок = финальная дата +1 т.к финальная не учитывается
		_periods = (int) ChronoUnit.MONTHS.between(_startdate, _stopdate);//кол-во полных месяцев в периоде
	}
	
	private double howDay()//сколько дней в месяце
	{	
		return _startdate.lengthOfMonth();
	}
	
	private double mathMoney()//подсчёт по формуле
	{
		return deposit*((interest/100)*(howDay()/_startdate.lengthOfYear())); //подневный расчет
	}
	
	private void addCapital(double capMoney, int i)//добавить сумму к финальной, внести строку в таблицу, сдвинуть дату
	{
		finalproc += capMoney; //финальные проценты - сумма всех процентов
		switch(proc)//добавление месяцев, в зависимости от частоты вывода в таблицу
		{
		case 0: _startdate = _startdate.plusMonths(1); break;//добавление месяца
		case 1: _startdate = _startdate.plusMonths(3); break;
		case 2: _startdate = _startdate.plusYears(1); break;
		}
		table[i][0]=_startdate.format(DateTimeFormatter.ofPattern("d/MM/YY"));//форматированный вывод даты в первую колонку
		table[i][1]=String.format("%.1f",capMoney); //формат до 2х знаков после запятой
		table[i][2]=String.format("%.1f",deposit);
	}
	
	private void simpleInterest()//простые проценты, для них выдача/невыдача денег не важна, так что proc игнорируется
	{

		table = new String[_periods][3]; //инициализация таблицы
		
		for(int i=0; i<_periods;i++) //добавление процентов каждый период
		{addCapital(mathMoney(), i); //считаем
		}
		deposit+=finalproc; //к итогу добавляем проценты
	}
	
	private void hardInterest()//сложные проценты
	{
		table = new String[_periods][3]; //инициализация таблицы
		double capMoney = 0; //накопление процентов за период
		int clock = 0; //для отсчёта периодов
		int period =0; //для ровного вывода строк
		for(int i=0; i<_periods;i++) //добавление процентов каждый период
		{
			capMoney+=mathMoney(); //накапливать за период
			clock++; //отсчёт периодов
			
			switch(proc)//проведение капитализации в зависимости от выбранного варианта
			{
			case 0: if (clock==1) 	{deposit +=capMoney; addCapital(capMoney, period); period++; capMoney=0; clock=0;} break;
			case 1: if (clock==3) 	{deposit +=capMoney; addCapital(capMoney, period); period++; capMoney=0; clock=0;} break;
			case 2: if (clock==12) 	{deposit +=capMoney; addCapital(capMoney, period); period++; capMoney=0; clock=0;} break;				
			}
			
		}
	}
	
}
