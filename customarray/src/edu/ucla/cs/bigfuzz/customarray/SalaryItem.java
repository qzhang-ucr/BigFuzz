package edu.ucla.cs.bigfuzz.customarray;

public class SalaryItem{
    private String zipcode;
    private int age;
    private int salary;

    public SalaryItem(String zipcode, int age, int salary)
    {
        this.zipcode = zipcode;
        this.age = age;
        this.salary = salary;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public void setSalary(int salary)
    {
        this.salary = salary;
    }
    public String getZipcode()
    {
        return this.zipcode;
    }
    public int getAge()
    {
        return this.age;
    }
    public int getSalary()
    {
        return this.salary;
    }

    @Override
    public String toString() {
        return this.zipcode+", "+this.age+", "+this.salary;
    }
}
