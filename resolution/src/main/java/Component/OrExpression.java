package Component;

import java.util.ArrayList;

//Tuyển sơ cấp
public class OrExpression {

    private ArrayList<Variable> listOfVariables = new ArrayList<Variable>();

    public boolean canBeRemovedFromKB = false;

    //
    public ArrayList<Variable> getListOfVariables() {
        return listOfVariables;
    }

    public boolean getIsTrue() {
        return canBeRemovedFromKB;
    }

    public OrExpression() {
        canBeRemovedFromKB = true;
    }

    public boolean isEmpty() {
        return listOfVariables.isEmpty();
    }

    public OrExpression(ArrayList<Variable> arrayOfVariable) {
        listOfVariables.addAll(arrayOfVariable);
    }

    public void add(Variable var) {
        listOfVariables.add(var);
    }

    //Chuẩn hóa 1 biểu thức chỉ có or loại các biến giống nhau và gán thành hằng số luôn đúng nếu tồn tại 2 biến đối nghịch
    public void normalize() {
        for(int i = 0; i < listOfVariables.size(); i++) {
            for(int j = i + 1; j < listOfVariables.size(); j++) {
                Variable variableI = listOfVariables.get(i);
                Variable variableJ = listOfVariables.get(j);
                if(variableI.getName().equals(variableJ.getName())) {
                    if(variableI.getIsNegative() != variableJ.getIsNegative()) {
                        canBeRemovedFromKB = true;
                        listOfVariables.clear();
                        return ;
                    }
                    else {
                        listOfVariables.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    public String toString() {
        String result = "( ";
        for(int i = 0; i < listOfVariables.size(); i++) {
            if(i == 0) {
                result = result + listOfVariables.get(i).toString();
            } else {
                result = result + " | " + listOfVariables.get(i).toString();
            }
        }
        result = result + " )";
        return result;
    }

    public OrExpression copy() {
        ArrayList<Variable> copyOfListVariable = new ArrayList<Variable>();
        for(Variable variable : listOfVariables) {
            copyOfListVariable.add((Variable)variable.copy());
        }
        return new OrExpression(copyOfListVariable);
    }
}
