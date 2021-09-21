import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Lead {
    private String ID;
    private String Name;
    private Date DOB;
    private Boolean Gender;
    private int Phone;
    private String Email;
    private String Address;
    private int Age;
    private ArrayList<Lead> leads_list = new ArrayList<Lead>();
    Scanner input = new Scanner(System.in);

    public Lead(){}

    public Lead (String id,String name, Date dob, Boolean gender, int phone, String email, String address) throws FileNotFoundException {
        this.ID = id;
        this.Name = name;
        this.DOB = dob;
        this.Gender = gender;
        this.Phone = phone;
        this.Email = email;
        this.Address = address;
    }

    public void setID(String id){
        this.ID = id;
    }

    public void setName(String name){
        this.Name = name;
    }

    public void setDOB(Date dob){
        this.DOB = dob;
    }

    public void setGender(Boolean gender){
        this.Gender = gender;
    }

    public void setPhone(int phone){
        this.Phone = phone;
    }

    public void setEmail(String email){
        this.Email = email;
    }

    public void setAddress(String address){
        this.Address = address;
    }

    public String getID(){
        return ID;
    }

    public String getName(){
        return Name;
    }

    public Date getDOB(){
        return DOB;
    }

    public Boolean getGender(){
        return Gender;
    }

    public int getPhone(){
        return Phone;
    }

    public String getEmail(){
        return Email;
    }

    public String getAddress(){
        return Address;
    }

    public int getAge(){ return Age;}

    public ArrayList<Lead> getLeads_list(){return leads_list;}

    public void Info(){
        System.out.println(ID + "," + Name + "," + DOB + "," + Gender + "," + Phone + "," + Email + "," + Address);
    }

    public void Load_lead() throws IOException, ParseException {
        BufferedReader csvreader_lead = new BufferedReader(new FileReader("D:\\Programming 1\\Final Assignment\\src\\Leads.csv"));
        String line;
        StringtoDate stringtoDate = new StringtoDate();
        while((line = csvreader_lead.readLine()) != null){
            String[] data = line.split(",");
            String id = data[0];
            String name = data[1];
            Date dob = stringtoDate.Convert_StringtoDate(data[2]);
            Boolean gender = Boolean.parseBoolean(data[3]);
            int phone = Integer.parseInt(data[4]);
            String email = data[5];
            String address = data[6];
            Lead customer = new Lead(id, name, dob, gender, phone, email, address);
            leads_list.add(customer);
        }
    }

    public void Save_lead() throws IOException {
        FileWriter fr_lead = new FileWriter("D:\\Programming 1\\Final Assignment\\src\\Leads.csv");
        BufferedWriter writer_lead = new BufferedWriter(fr_lead);
        PrintWriter out = new PrintWriter(writer_lead);
        DatetoString datetoString = new DatetoString();
        for(Lead lea : leads_list){
            out.write(lea.getID());
            out.write(",");
            out.write(lea.getName());
            out.write(",");
            out.write(datetoString.Convert_DatetoString(lea.getDOB()));
            out.write(",");
            out.write(String.valueOf(lea.getGender()));
            out.write(",");
            out.write(String.valueOf(lea.getPhone()));
            out.write(",");
            out.write(lea.getEmail());
            out.write(",");
            out.write(lea.getAddress());
            out.write("\n");
        }
        out.close();
    }

    public void View_lead() throws IOException{
        for (Lead lea : leads_list){
            lea.Info();
        }
    }

    public void Add_lead() throws IOException, ParseException {
        int index_lastID;
        Auto_ID auto_id = new Auto_ID();
        StringtoDate stringtoDate = new StringtoDate();
        if(leads_list == null){
            leads_list = new ArrayList<Lead>();
        }
        else {
            index_lastID = leads_list.size() - 1;
            String lastID = leads_list.get(index_lastID).getID();
            auto_id.Auto_Update(lastID);

            String id = auto_id.getLead_ID();

            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a name: ");
            String name = read.readLine();

            System.out.print("Enter Day: ");
            String day = read.readLine();
            System.out.print("Enter Month: ");
            String month = read.readLine();
            System.out.print("Enter Year: ");
            String year = read.readLine();
            String date = "";
            date = date.concat(month).concat("/").concat(day).concat("/").concat(year);
            System.out.println(date);
            Date dob = stringtoDate.Convert_StringtoDate(date);

            System.out.print("Enter Phone: ");
            String phone_num = read.readLine();
            int phone = Integer.parseInt(phone_num);

            System.out.print("Enter a Gender [male/female]: ");
            String gender_bool = read.readLine();
            Boolean gender;
            if(gender_bool.equals("male") || gender_bool.equals("Male")){
                gender = true;
            }
            else {
                gender = false;
            }

            System.out.print("Enter an email: ");
            String email = read.readLine();

            System.out.print("Enter an address: ");
            String address = read.readLine();

            Lead customer = new Lead(id, name, dob, gender, phone, email, address);
            leads_list.add(customer);
            Save_lead();
        }
    }

    public void Delete_lead() throws IOException{
        ArrayList<String> delete_leadID = new ArrayList<>();
        int size_lead = 0;
        int index_delete;
        int contain = 0;
        String delete_ID;
        String LeadID_in_Inter;
        if(leads_list != null){
            size_lead = leads_list.size();
        }

        for(Lead lea : leads_list){
            delete_leadID.add(lea.getID());
        }

        View_lead();
        System.out.println("**************************************************");
        System.out.print("Please enter an Lead's ID that you want to delete: ");
        delete_ID = input.next();
        index_delete = delete_leadID.indexOf(delete_ID);

        for(Interaction inter : Main.interact.getInteraction_list()){
            if(inter.getLead().equals(delete_ID)){
                contain = contain + 1;
            }
        }

        if(index_delete == -1){
            System.out.println("There is no matching lead customer with this ID");
        }

        if(contain > 0){
            System.out.println("You cannot perform the Delete function because this lead has interactions");
            System.out.println("You will need to delete all interactions contain " + delete_ID + " first");
        }

        else {
            leads_list.remove(index_delete);
            Save_lead();
        }

        if(size_lead > leads_list.size()){
            System.out.println("The lead is completely deleted");
        }
    }

    public void Update_lead() throws IOException, ParseException {
        ArrayList<String> update_leadID = new ArrayList<>();
        int index_update;
        String update_ID;
        StringtoDate stringtoDate = new StringtoDate();

        for(Lead lea : leads_list){
            update_leadID.add(lea.getID());
        }

        View_lead();
        System.out.println("**************************************************");
        System.out.print("Please enter an Lead's ID that you want to update info: ");
        update_ID = input.next();
        index_update = update_leadID.indexOf(update_ID);

        if(index_update == -1){
            System.out.println("There is no matching lead customer with this ID");
        }
        else {
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a new name: ");
            String name = read.readLine();

            System.out.print("Enter Day: ");
            String day = read.readLine();
            System.out.print("Enter Month: ");
            String month = read.readLine();
            System.out.print("Enter Year: ");
            String year = read.readLine();
            String date = "";
            date = date.concat(month).concat("/").concat(day).concat("/").concat(year);
            System.out.println(date);
            Date dob = stringtoDate.Convert_StringtoDate(date);

            System.out.print("Enter a new Phone: ");
            String phone_num = read.readLine();
            int phone = Integer.parseInt(phone_num);

            System.out.print("Enter a new Gender [male/female]: ");
            String gender_bool = read.readLine();
            Boolean gender;
            if(gender_bool.equals("male") || gender_bool.equals("Male")){
                gender = true;
            }
            else {
                gender = false;
            }

            System.out.print("Enter a new email: ");
            String email = read.readLine();

            System.out.print("Enter a new address: ");
            String address = read.readLine();

            Lead customer = new Lead(update_ID, name, dob, gender, phone, email, address);
            leads_list.set(index_update,customer);
            Save_lead();
            System.out.println("Successfully Updated");
        }

    }

    public int Calculate_age(){
        Date date = new Date();

        int diff = (int) ((date.getTime() - this.DOB.getTime()) / (1000 * 60 * 60 * 24));

        this.Age = diff / 365;

        return Age;
    }
}
