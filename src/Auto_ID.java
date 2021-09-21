
public class Auto_ID {
    private String lead_ID;
    private String inter_ID;

    public String getLead_ID(){
        return lead_ID;
    }

    public String getInter_ID(){
        return inter_ID;
    }

    public void Auto_Update(String str_ID){
        String[] id = str_ID.split("_");
        int i = Integer.parseInt(id[1]) + 1;
        String s_i = String.valueOf(i);
        if(i <= 9){
            if(id[0].equals("lead")){
                String s1 = "lead_00";
                s1 = s1.concat(s_i);
                this.lead_ID = s1;
            }
            else if(id[0].equals("inter")){
                String s1 = "inter_00";
                s1 = s1.concat(s_i);
                this.inter_ID = s1;
            }
        }
        if (i > 9 && i <= 99){
            if(id[0].equals("lead")){
                String s2 = "lead_0";
                s2 = s2.concat(s_i);
                this.lead_ID = s2;
            }
            else if(id[0].equals("inter")){
                String s2 = "inter_0";
                s2 = s2.concat(s_i);
                this.inter_ID = s2;
            }
        }
        if(i > 99 && i <= 999){
            if(id[0].equals("lead")){
                String s3 = "lead_";
                s3 = s3.concat(s_i);
                this.lead_ID = s3;
            }
            else if(id[0].equals("inter")){
                String s3 = "inter_";
                s3 = s3.concat(s_i);
                this.inter_ID = s3;
            }
        }
    }
}
