import java.util.ArrayList;

import org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderColumn;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;


public class Functions_K {
    public int D;
    public int f;
    ArrayList<Double> UpperBoundaries = new ArrayList<Double>(); // boundaries
    ArrayList<Double> LowerBoundaries = new ArrayList<Double>();
    double sum1 = 0;
    double sum2 = 0;
    double sum3 = 0;
    double x;
    double x1;
    double z;
    double z1;

    String fnName;

    ArrayList <Double> o  = new ArrayList <Double>(); // shifted origin
    ArrayList <Double> sum  = new ArrayList <Double>();
    ArrayList <Double> fbias  = new ArrayList <Double>();
    ArrayList <Double> bias  = new ArrayList <Double>();
    ArrayList <Double> lambda  = new ArrayList <Double>();
    ArrayList <Double> sigma  = new ArrayList <Double>();
    ArrayList <Integer> compositeFn  = new ArrayList <Integer>();
    ArrayList <DenseMatrix64F> M  = new ArrayList <DenseMatrix64F>();
    ArrayList <Double> x5  = new ArrayList <Double>();
    ArrayList <Double> aPower  = new ArrayList <Double>(); // array of powers of a fn18 Weirstrass
    ArrayList <Double> bPower  = new ArrayList <Double>(); // array of powers of b fn18 Weirstrass
    double fn18Term; // fn term for fn18 Weirstrass
    double PI = Math.PI;
    DenseMatrix64F o10xD = new DenseMatrix64F(10,100);
    DenseMatrix64F Orthogonal = new DenseMatrix64F(D,D);
    DenseMatrix64F LT = new DenseMatrix64F(D,D);
    DenseMatrix64F I = new DenseMatrix64F(D,D);
    DenseMatrix64F A = new DenseMatrix64F(D,D);
    DenseMatrix64F B = new DenseMatrix64F(D,D);
    DenseMatrix64F alpha = new DenseMatrix64F(1,D);
    double Fitness = 0;

    public Functions_K(int f,int D, ArrayList<Double> UpperBoundaries, ArrayList<Double> LowerBoundaries){
        setName(f);
        this.D = D;
        this.f = f;
        this.UpperBoundaries = UpperBoundaries;
        this.LowerBoundaries = LowerBoundaries;
        this.o = newOrigin();
        this.Orthogonal = createOrthogonal(D);
        setBias();
        powerArray(aPower,0.5,20);
        powerArray(bPower,3.0,20);
        for(int i=0;i<D;i++){
            x5.add(5.0);
        }
        if(f==12){
            this.A = createRandomMatrix(D,D,500);
            o = originAtBoundary();
        }
        if(f==14 || f==21){
            this.o = newNegativeOrigin();
            this.LT = createLinearT(3,D);
        }
        if(f==15){
            this.LT = createLinearT(100,D);
            this.o = originAtBoundary();
        }
        if(f==17){
            this.LT = createLinearT(2,D);
        }
        if(f==18){
            this.LT = createLinearT(5,D);
        }
        sum2=0;
        for(int k=0;k<=20;k++){
            sum2 = sum2 + (aPower.get(k)*Math.cos(2*PI*(bPower.get(k))*0.5));
        }
        fn18Term = sum2;
        sum2=0;

        if(f==19){
            this.A = createRandomMatrix(D,D,100);
            this.B = createRandomMatrix(D,D,100);
            this.alpha = createRandomMatrix(1,D,PI);

            double a;
            double b;
            double alph;

            for(int i=0;i<D;i++){ // 30 dimensions
                sum1 = 0;
                for(int j =0; j<D;j++){
                    a = A.get(i, j);
                    b = B.get(i,j);
                    alph = alpha.get(0, j);
                    sum1 = sum1 + ((a*Math.sin(alph)) + (b*Math.cos(alph)));
                }
                sum.add(sum1);

            }
        }

		/* 1 = Ratigrin
		 * 2 = Weirstrass
		 * 3 = Griewank
		 * 4 = Ackley
		 * 5 = Sphere
		 * 6 = expandedSchaffer
		 * 7 = expandedGriewankRosenbrock
		 * 8 = non continuous expanded scaffer
		 * 9 = non continuous rastrigin
		 * 10 = High conditional elliptic
		 * 11 = sphere with noise */

        if(f==22){
            for(int i=0;i<10;i++){
                this.sigma.add(1.0);
                M.add(createIdentity(D));
            }
            for(int i=1;i<6;i++){
                compositeFn.add(i);
                compositeFn.add(i);
            }

        }

        if(f==23 || f==24){
            for(int i=0;i<10;i++){
                this.sigma.add(1.0);
                M.add(createLinearT(2,D));
            }
            for(int i=1;i<6;i++){
                compositeFn.add(i);
                compositeFn.add(i);
            }
        }

        if(f==25 || f==27){
            this.sigma.add(1.0);
            this.sigma.add(2.0);
            this.sigma.add(1.5);
            this.sigma.add(1.5);
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(1.5);
            this.sigma.add(1.5);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
            this.o = originAtBoundary();
        }
        if(f==26){
            this.sigma.add(0.1);
            this.sigma.add(2.0);
            this.sigma.add(1.5);
            this.sigma.add(1.5);
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(1.5);
            this.sigma.add(1.5);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
        }
        if(f==25 || f==26 || f==27){
            M.add(createRotation(2,D));
            M.add(createRotation(3,D));
            M.add(createRotation(2,D));
            M.add(createRotation(3,D));
            M.add(createRotation(2,D));
            M.add(createRotation(3,D));
            M.add(createRotation(20,D));
            M.add(createRotation(30,D));
            M.add(createRotation(200,D));
            M.add(createRotation(300,D));
            compositeFn.add(4);
            compositeFn.add(4);
            compositeFn.add(1);
            compositeFn.add(1);
            compositeFn.add(5);
            compositeFn.add(5);
            compositeFn.add(2);
            compositeFn.add(2);
            compositeFn.add(3);
            compositeFn.add(3);
        }

        if(f==28 || f==29 || f==30){
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(1.0);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
            this.sigma.add(2.0);
            compositeFn.add(6);
            compositeFn.add(6);
            compositeFn.add(1);
            compositeFn.add(1);
            compositeFn.add(7);
            compositeFn.add(7);
            compositeFn.add(2);
            compositeFn.add(2);
            compositeFn.add(3);
            compositeFn.add(3);
        }
        if(f==28 || f==30){
            for(int i=0;i<10;i++){
                M.add(createOrthogonal(D));
            }
        }
        if(f==29){
            M.add(createRotation(10,D));
            M.add(createRotation(20,D));
            M.add(createRotation(50,D));
            M.add(createRotation(100,D));
            M.add(createRotation(200,D));
            M.add(createRotation(1000,D));
            M.add(createRotation(2000,D));
            M.add(createRotation(3000,D));
            M.add(createRotation(4000,D));
            M.add(createRotation(5000,D));
        }

        if(f==31 || f==32){
            for(int i=0;i<10; i++){
                this.sigma.add(2.0);
            }
            M.add(createRotation(100,D));
            M.add(createRotation(50,D));
            M.add(createRotation(30,D));
            M.add(createRotation(10,D));
            M.add(createRotation(5,D));
            M.add(createRotation(5,D));
            M.add(createRotation(4,D));
            M.add(createRotation(3,D));
            M.add(createRotation(2,D));
            M.add(createRotation(2,D));
            compositeFn.add(2);
            compositeFn.add(6);
            compositeFn.add(7);
            compositeFn.add(4);
            compositeFn.add(1);
            compositeFn.add(3);
            compositeFn.add(8);
            compositeFn.add(9);
            compositeFn.add(10);
            compositeFn.add(11);
        }
        setLambda(f);

        this.I = createIdentity(D);
        o10xD.reshape(10, D);
        ArrayList <Double> tempO = new ArrayList <Double>();
        for(int i=0;i<10;i++){
            tempO = newOrigin();
            for(int j=0;j<D;j++){
                this.o10xD.set(0, j, tempO.get(j));
            }
        }
        setCompositeBias();
    }

    public double FitnessCheck(ArrayList <Double> Positions){
        Fitness = 0;
        double sum1 = 0;
        double sum2 = 0;
        DenseMatrix64F B12 = new DenseMatrix64F(D,D); // only needed for fn 12
        DenseMatrix64F M = new DenseMatrix64F(D,D);
        ArrayList <Double> zArray  = new ArrayList <Double>();
        if(f==1){ // spherical function
            Fitness = sphere(Positions,D) + fbias.get(f);
            this.fnName = "Sphere";
        }

        else if(f==2){ // Rosenbrock function
            Fitness = rosenbrock(Positions,0,D) + fbias.get(f);
            this.fnName = "Rosenbrock";
        }

        else if(f==3){ // Ackley function
            Fitness = ackley(Positions,D)+ fbias.get(f);
            this.fnName = "Ackley";
        }

        else if(f==4){ // Griewank function
            Fitness = griewank(Positions,D)+ fbias.get(f);
            this.fnName = "Griewank";
        }

        else if(f==5){ // Rastrigin function
            Fitness = rastrigin(Positions,D)+ fbias.get(f);
            this.fnName = "Rastrigin";
        }

        else if(f==6){ // Schaffer (2D) function
            Fitness = schaffer(Positions.get(0), Positions.get(1)) + fbias.get(f);
            this.fnName = "Schaffer2D";
        }

        else if(f==7){ // Griewank 10D function
            Fitness=griewank(Positions,10) + fbias.get(f);
            this.fnName = "Griewank10D";
        }

        else if(f==8){ // Shifted sphere function
            shiftOrigin(Positions,zArray,0);
            Fitness = sphere(zArray,D) + fbias.get(f);
            this.fnName = "ShiftedSphere";
        }

        else if(f==9){ // Shifted schwefel function
            shiftOrigin(Positions,zArray,0);
            z = zArray.get(0);
            sum2= z ;
            Fitness= sum2*sum2;
            for(int i=1;i<D;i++){ // 30 dimensions
                z = zArray.get(i);
                sum1 = sum2;
                sum2 = sum1+z;
                Fitness = Fitness + (sum2*sum2);
            }
            Fitness = Fitness + fbias.get(f);
            this.fnName = "ShiftedSchwefel";
        }

        else if(f==10){ // Shifted rotated eliptical function
            shiftAndRotate(Orthogonal, Positions, zArray);
            Fitness = elliptic(zArray,D) + fbias.get(f);
            this.fnName = "ShiftedRotatedEliptical";
        }

        else if(f==11){ // Shifted schwefel function with noise
            shiftOrigin(Positions,zArray,0);
            z = zArray.get(0);
            sum2= z;
            Fitness= sum2*sum2;
            for(int i=1;i<D;i++){ // 30 dimensions
                z = zArray.get(i);
                sum1 = sum2;
                sum2 = sum1+z;
                Fitness = Fitness + (sum2*sum2);
            }
            Fitness = Fitness*(1+(0.4*Math.random())) + fbias.get(f);
            this.fnName = "ShiftedNoiseSchwefel";
        }

        else if(f==12){ // Shifted schwefel function with global optimum on bounds
            Fitness = Double.NEGATIVE_INFINITY;
            double sum;
            for(int i =0;i<D;i++){
                sum = 0;
                for(int j=0;j<D;j++){ // 30 dimensions
                    B12.set(i, j, (A.get(i, j)*o.get(j)));
                }
                for(int j=0;j<D;j++){
                    x= Positions.get(j);
                    sum = sum + ((A.get(i,j)*x) - B12.get(i,j));
                }
                sum = Math.abs(sum);
                if (Fitness<sum){
                    Fitness=sum;
                }
            }
            Fitness = Fitness  + fbias.get(f);
            this.fnName = "OptimumOnBoundsSchwefel";
        }


        else if(f==13){ // Shifted rosenbrock function
            shiftOrigin(Positions,zArray,1);
            Fitness = rosenbrock(zArray,0,D) + fbias.get(f);
            this.fnName = "ShiftedRosenbrock";
        }

        else if(f==14){ // Shifted rotated griewank function without bounds
            CommonOps.scale((1+(0.3*Math.random())), LT, M);
            shiftAndRotate(M, Positions, zArray);
            Fitness = griewank(zArray,D) + fbias.get(f);
            this.fnName = "OptimumOnBoundsRotatedGriewank";
        }

        else if(f==15){ // Shifted rotated Ackley function global optimum on bounds
            shiftAndRotate(LT, Positions, zArray);
            Fitness = ackley(zArray,D)  + fbias.get(f);
            this.fnName = "OptimumOnBoundsRotatedAckley";
        }

        else if(f==16){ //Shifted Rastrigin
            shiftOrigin(Positions,zArray,0);
            Fitness = rastrigin(zArray,D)  + fbias.get(f);
            this.fnName = "ShiftedRastrigin";
        }

        else if(f==17){ //Shifted & Rotated Rastrigin
            shiftAndRotate(LT, Positions, zArray);
            Fitness = rastrigin(zArray,D)  + fbias.get(f);
            this.fnName = "ShiftedRotatedRastrigin";
        }

        else if(f==18){ //Shifted & Rotated Weirstrass
            shiftAndRotate(LT, Positions, zArray);
            Fitness  = weirstrass(zArray,D)  + fbias.get(f);
            this.fnName = "ShiftedRotatedWeirstrass";
        }

        else if(f==19){ // Schwefel
            double a;
            double b;
            for(int i=0;i<D;i++){ // 30 dimensions
                sum2 = 0;
                for(int j =0; j<D;j++){
                    x=Positions.get(j);
                    a = A.get(i, j);
                    b = B.get(i,j);
                    sum2 = sum2 + ((a*Math.sin(x))  +  (b*Math.cos(x)));
                }
                Fitness = Fitness + (sum.get(i)-sum2)*(sum.get(i)-sum2) ; // sum is an arraylist of unchanging terms
            }
            Fitness = Fitness + fbias.get(f);
            this.fnName = "Schwefel";
        }

        else if(f==20){ // Shifted expanded  griewank plus rosenbrock function
            shiftOrigin(Positions,zArray,1);
            Fitness = expandedGriewankRosenbrock(zArray,D) + fbias.get(f);
            this.fnName = "ShiftedExpandedGriewankRosenbrock";
        }


        else if(f==21){ // Shifted rotated expanded  schaffer
            shiftAndRotate(LT, Positions, zArray);
            Fitness = expandedSchaffer(zArray,D) + fbias.get(f);
            this.fnName = "ShiftedRotatedExpandedSchaffer";
        }


        else if(f==22){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F15";
        }

        else if(f==23){ // composition function (rotated version of 22)
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F16";
        }

        else if(f==24){ // composition function (noise added to 23)
            Fitness = (F15(Positions,D)*(1+(0.2*Math.random()))) + fbias.get(f);
            this.fnName = "F17";
        }

        else if(f==25){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F18";
        }

        else if(f==26){ // composition function (diff parameters from 25)
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F19";
        }

        else if(f==27){ // composition function (25 with optimum moved to boundary)
            shiftOrigin(Positions,zArray,0);
            Fitness = F15(zArray,D) + fbias.get(f);
            this.fnName = "F20";
        }

        else if(f==28){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F21";
        }

        else if(f==29){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F22";
        }

        else if(f==30){ // composition function (non continuous 29)
            zArray = round(Positions);
            Fitness = F15(zArray,D) + fbias.get(f);
            this.fnName = "F23";
        }

        else if(f==31){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F24";
        }

        else if(f==32){ // composition function
            Fitness = F15(Positions,D) + fbias.get(f);
            this.fnName = "F25";
        }

        else{
            Fitness = 0;
            System.out.println("Invalid function");
        }
        return Fitness;
    }



    public DenseMatrix64F createOrthogonal(int D){
        DenseMatrix64F M = new DenseMatrix64F(D,D);
        QRDecompositionHouseholderColumn qr = new QRDecompositionHouseholderColumn ();
        for (int i=0;i<D; i++){
            for(int j=0;j<D; j++){
                M.set(i, j, Math.random());
            }
        }
        qr.decompose(M);
        qr.setToQ(M);
        return M;
    }

    public DenseMatrix64F createRotation(int c, int D){
        DenseMatrix64F M = new DenseMatrix64F(D,D);
        QRDecompositionHouseholderColumn qr = new QRDecompositionHouseholderColumn ();
        M = createLinearT(c,D);
        qr.decompose(M);
        qr.setToQ(M);
        return M;
    }

    public DenseMatrix64F createLinearT(int c, int D){ // M = P*N*Q, P & Q are orthogonal, N is diagonal
        ArrayList<Double> u = new ArrayList<Double>();
        double d;
        double uMax = 1;
        double uMin = D;
        double ui;
        DenseMatrix64F M = new DenseMatrix64F(D,D);
        DenseMatrix64F P = createOrthogonal(D);
        DenseMatrix64F Q = createOrthogonal(D);
        DenseMatrix64F N = new DenseMatrix64F(D,D);
        DenseMatrix64F Temp = new DenseMatrix64F(D,D);

        //QRDecompositionHouseholderColumn qr = new QRDecompositionHouseholderColumn ();
        for (int i=0;i<D; i++){
            ui= (Math.random()*(D-1))+1;
            if (ui>uMax){
                uMax=ui;
            }
            if (ui<uMin){
                uMin=ui;
            }
            u.add(ui);
        }

        for (int i=0;i<D; i++){
            for(int j=0;j<D; j++){
                if(i==j){
                    d = Math.pow(c,(u.get(i)-uMin)/(uMax-uMin));
                    N.set(i, j, d);
                }
                else{
                    N.set(i,j,0);
                }
            }
        }
        CommonOps.mult(P, Q, Temp);
        CommonOps.mult(Temp, N, M);

        return M;
    }

    public ArrayList <Double> newOrigin(){
        ArrayList <Double> o = new ArrayList <Double>();
        for (int i = 0; i<D; i++){
            o.add((Math.random()*(UpperBoundaries.get(i)-LowerBoundaries.get(i)))+LowerBoundaries.get(i));
        }
        return o;
    }

    public ArrayList <Double> newNegativeOrigin(){
        ArrayList <Double> o = new ArrayList <Double>();
        for (int i = 0; i<D; i++){
            o.add(Math.random()*UpperBoundaries.get(i)*-1);
        }
        return o;
    }

    public ArrayList <Double> newOrigin50(){
        ArrayList <Double> o = new ArrayList <Double>();
        for (int i = 0; i<D; i++){
            o.add(50.0);
        }
        return o;
    }

    public ArrayList <Double> originAtBoundary(){
        ArrayList <Double> o = new ArrayList <Double>();
        for(int i=0;i<D;i++){
            if(i<(D/2)){
                o.add(i, UpperBoundaries.get(i));
            }
            else{
                o.add(i, LowerBoundaries.get(i));
            }
        }
        return o;
    }

    public void shiftOrigin(ArrayList <Double> Positions, ArrayList <Double> zArray, int n){
        for(int i=0;i<D;i++){ // 30 dimensions
            x = Positions.get(i);
            z = x - o.get(i) +n;
            zArray.add(z);
        }
    }

    public void shiftAndRotate(DenseMatrix64F T, ArrayList <Double> Positions, ArrayList <Double> zArray){
        DenseMatrix64F X_O = new DenseMatrix64F(1,D);
        DenseMatrix64F Z = new DenseMatrix64F(1,D);
        for(int i=0;i<D;i++){
            x = Positions.get(i);
            X_O.set(0, i, (x - o.get(i)));
        }
        CommonOps.mult(X_O, T, Z); // z = (x-o)*M , M is Linear transform
        for(int i=0;i<D;i++){ // 30 dimensions
            z=Z.get(0,i);
            zArray.add(z);
        }
    }

    public DenseMatrix64F createRandomMatrix(int row, int col, double range){
        DenseMatrix64F N = new DenseMatrix64F(row,col);
        for (int i=0;i<row; i++){
            for(int j=0;j<col; j++){
                N.set(i, j, (Math.random()*range*2)-range);
            }
        }
        return N;
    }

    public void powerArray(ArrayList <Double> ans, double n, int k){
        for(int i=0;i<=k;i++){
            ans.add(Math.pow(n, i));
        }
    }

    public DenseMatrix64F createIdentity(int D){
        DenseMatrix64F N = new DenseMatrix64F(D,D);
        for (int i=0;i<D; i++){
            for(int j=0;j<D; j++){
                if(i==j){
                    N.set(i, j, 1);
                }
                else{
                    N.set(i, j, 0);
                }
            }
        }
        return N;
    }

    public double sphere(ArrayList <Double> z, int D){
        double sum1 = 0.0;
        for(int i = 0; i<D; i++){
            x = z.get(i);
            sum1 = sum1 + ((x)*(x));
        }
        return sum1;
    }

    public double ackley(ArrayList <Double> z, int D){
        double sum1 = 0.0;
        double sum2 = 0.0;
        double sum3 = 0.0;
        for(int i=0;i<D;i++){ // 30 dimensions
            x = z.get(i);
            sum1 = sum1 + Math.pow(x, 2);
            sum2 = sum2 + Math.cos(2*PI*x);
        }
        sum3=-(20*Math.exp(-0.2*Math.sqrt(sum1/D))) - (Math.exp(sum2/D)) + 20 + (Math.E);
        return sum3;
    }

    public double griewank(ArrayList <Double> z, int D){
        double sum1 = 0.0;
        double sum2=1.0;
        for(int i=0;i<D;i++){ // 30 dimensions
            x = z.get(i);
            sum1 = sum1 + (x*x);
            sum2 = sum2 * Math.cos(x/Math.sqrt(i+1));
        }
        sum3=1+(sum1/4000)-(sum2);
        return sum3;
    }

    public double rastrigin(ArrayList <Double> z, int D){
        double sum3=0;
        for(int i=0;i<D;i++){ // 30 dimensions
            x = z.get(i);
            sum3 = sum3 + (x*x) - 10*Math.cos(2*PI*x) +10;
        }
        return sum3;
    }

    public double rosenbrock(ArrayList <Double> z,int s, int D){
        double sum1 =0;
        double sum2 = 0;
        double sum3 =0;
        for(int i=s;i<D-1;i++){ // 30 dimensions
            x = z.get(i);
            x1 = z.get(i+1);
            sum1 = Math.pow(    ((x*x)-(x1)), 2);
            sum2 = Math.pow((x-1), 2);
            sum3 = sum3 + (100*sum1) +sum2;
        }
        return sum3;
    }

    public double schaffer(double x, double y){
        double sum1 =0;
        double sum2 = 0;
        double sum3 =0;
        sum1 = Math.pow(  Math.sin(Math.sqrt((x*x)+(y*y))), 2)  - 0.5;
        sum2 = Math.pow((1+(0.0001*((x*x)+(y*y)))), 2);
        sum3 = 0.5 + (sum1/sum2);
        return sum3;
    }

    public double weirstrass(ArrayList <Double> z, int D){
        double sum1 =0;
        double sum2 = 0;
        int km = 20;
        double t1;
        for(int i=0;i<D;i++){ // 30 dimensions
            sum1 = 0;
            x = z.get(i);
            for(int k=0;k<=km;k++){
                t1 = Math.cos(2*PI*(bPower.get(k))*(x+0.5));
                sum1 = sum1 + (aPower.get(k)*t1);
            }
            sum2 = sum2 +sum1;
        }
        sum2 = sum2 - (D*fn18Term);
        return sum2;
    }

    public double elliptic(ArrayList <Double> zArray, int D){
        double z;
        double sum1 =0;
        for(int i=0;i<D;i++){ // 30 dimensions
            z=zArray.get(i);
            sum1 = sum1 + Math.pow(Math.pow(10,6),(i-1)/(D-1))*(z*z);
        }
        return sum1;
    }

    public double expandedGriewankRosenbrock(ArrayList <Double> z, int D){
        double sum3 =0;
        for(int i=0;i<D-1;i++){ // 30 dimensions
            sum3 = sum3 + griewank1D(rosenbrock2D(z.get(i), z.get(i+1)));
        }
        sum3 = sum3 + griewank1D(rosenbrock2D(z.get(D-1), z.get(0)));
        return sum3;

    }

    public double expandedSchaffer(ArrayList <Double> zArray, int D){
        double sum1 =0;
        for(int i=0;i<D-1;i++){ // 30 dimensions
            z = zArray.get(i);
            z1 = zArray.get(i+1);
            sum1  = sum1 + schaffer(z,z1);
        }
        z = zArray.get(D-1);
        z1 = zArray.get(0);
        sum1  = sum1 + schaffer(z,z1);
        return sum1;
    }

    public void setBias(){
        for(int i = 0;i<8;i++){
            fbias.add(0.0);
        }
        for (int i = 0;i<4;i++){
            fbias.add(-450.0);
        }
        fbias.add(-310.0);
        fbias.add(390.0);
        fbias.add(-180.0);
        fbias.add(-140.0);
        fbias.add(-330.0);
        fbias.add(-330.0);
        fbias.add(90.0);
        fbias.add(-460.0);
        fbias.add(-130.0);
        fbias.add(-300.0);
        fbias.add(120.0);
        fbias.add(120.0);
        fbias.add(120.0);
        fbias.add(10.0);
        fbias.add(10.0);
        fbias.add(10.0);
        fbias.add(360.0);
        fbias.add(360.0);
        fbias.add(360.0);
        fbias.add(260.0);
        fbias.add(260.0);
    }

    public double rosenbrock2D(double x, double y) {
        double temp1 = (x * x) - y;
        double temp2 = x - 1.0;
        return ((100.0 * temp1 * temp1) + (temp2 * temp2));
    }

    public double griewank1D(double x) {
        return (((x * x) / 4000.0) - Math.cos(x) + 1.0);
    }

    public void setLambda(int f){
        if(f==22|| f==23||f==24){
            lambda.add(1.0);
            lambda.add(1.0);
            lambda.add(10.0);
            lambda.add(10.0);
            lambda.add(5.0/60.0);
            lambda.add(5.0/60.0);
            lambda.add(5.0/32.0);
            lambda.add(5.0/32.0);
            lambda.add(5.0/100.0);
            lambda.add(5.0/100.0);
        }
        if(f==25 || f==27){
            lambda.add(10.0/32.0);
            lambda.add(5.0/32.0);
            lambda.add(2.0);
            lambda.add(1.0);
            lambda.add(1.0/10.0);
            lambda.add(1.0/20.0);
            lambda.add(20.0);
            lambda.add(10.0);
            lambda.add(1.0/6.0);
            lambda.add(1.0/12.0);
        }
        if(f==26){
            lambda.add(1.0/64.0);
            lambda.add(5.0/32.0);
            lambda.add(2.0);
            lambda.add(1.0);
            lambda.add(1.0/10.0);
            lambda.add(1.0/20.0);
            lambda.add(20.0);
            lambda.add(10.0);
            lambda.add(1.0/6.0);
            lambda.add(1.0/12.0);
        }
        if(f==28 || f==29 || f==30){
            lambda.add(1.0/4.0);
            lambda.add(1.0/20.0);
            lambda.add(5.0);
            lambda.add(1.0);
            lambda.add(5.0);
            lambda.add(1.0);
            lambda.add(50.0);
            lambda.add(10.0);
            lambda.add(1.0/8.0);
            lambda.add(1.0/40.0);
        }
        if(f==31 || f==32){
            lambda.add(10.0);
            lambda.add(1.0/4.0);
            lambda.add(1.0);
            lambda.add(5.0/32.0);
            lambda.add(1.0);
            lambda.add(1.0/20.0);
            lambda.add(1.0/10.0);
            lambda.add(1.0);
            lambda.add(1.0/20.0);
            lambda.add(1.0/20.0);
        }
    }

    public void setCompositeBias(){
        bias.add(0.0);
        bias.add(100.0);
        bias.add(200.0);
        bias.add(300.0);
        bias.add(400.0);
        bias.add(500.0);
        bias.add(600.0);
        bias.add(700.0);
        bias.add(800.0);
        bias.add(900.0);

    }
    public ArrayList <Double> setWeights(ArrayList <Double> zArray){
        ArrayList <Double> w  = new ArrayList <Double>();
        double temp = 0;
        int wMaxPos = 0;
        double wMax = 0;
        double wTemp = 0;
        double wSum = 0;
        for(int i=0;i<10;i++){
            for(int j=0;j<D;j++){
                z = zArray.get(j);
                temp = temp + (z*z);
            }
            temp =  Math.exp(-(temp/(2*D*(sigma.get(i))*(sigma.get(i)))));
            if(i==0){
                wMax=temp;
            }
            if(wMax<temp){
                wMax = temp;
                wMaxPos=i;
            }
            w.add(temp);
        }
        for(int i=0;i<10;i++){
            if(i!=wMaxPos){
                wTemp = w.get(i);
                w.set(i, wTemp*(1-(Math.pow(w.get(wMaxPos),10))));
            }
            wTemp = w.get(i);
            wSum = wSum + wTemp;
        }

        for(int i=0;i<10;i++){
            wTemp = w.get(i);
            w.set(i,wTemp/wSum);
        }
        return w;
    }

    public double F15(ArrayList <Double> zArray, int D) {
		/* 1 = Ratigrin
		 * 2 = Weirstrass
		 * 3 = Griewank
		 * 4 = Ackley
		 * 5 = Sphere
		 * 6 = expandedSchaffer
		 * 7 = expandedGriewankRosenbrock
		 * 8 = non continuous expanded scaffer
		 * 9 = non continuous rastrigin
		 * 10 = High conditional elliptic
		 * 11 = sphere with noise */

        double sum1 =0;
        double sum2 = 0;
        int n =10;
        double C = 2000.0;
        double fmax ;
        double ft ;
        ArrayList <Double> X5TranformArray  = new ArrayList <Double>();
        ArrayList <Double> zTranformArray  = new ArrayList <Double>();
        ArrayList <Double> w  = new ArrayList <Double>();
        DenseMatrix64F X5 = new DenseMatrix64F(1,D);
        DenseMatrix64F X5Tranform = new DenseMatrix64F(1,D);
        DenseMatrix64F z = new DenseMatrix64F(1,D);
        DenseMatrix64F zTranform = new DenseMatrix64F(1,D);
        sum2=0;

        for(int i=0;i<n;i++){ // 30 dimensions
            for(int j=0;j<D;j++){
                X5.set(0,j, x5.get(j)/lambda.get(i)); // x5 = x5/lambda
            }
            CommonOps.mult(X5, M.get(i), X5Tranform); // X5tranform = (x5/lambda) * M
            while (X5TranformArray.size()>0) {
                X5TranformArray.remove(0);
            }
            for(int j=0;j<D;j++){
                X5TranformArray.add(X5Tranform.get(0,j));
            }


            for(int j=0;j<D;j++){
                z.set(0,j, zArray.get(j)/lambda.get(i));
            }
            CommonOps.mult(z, M.get(i), zTranform);
            while (zTranformArray.size()>0) {
                zTranformArray.remove(0);
            }
            for(int j=0;j<D;j++){
                zTranformArray.add(zTranform.get(0,j));
            }
            w = setWeights(zArray);


            if(compositeFn.get(i)==1){
                fmax = rastrigin(X5TranformArray,D);
                sum1 = rastrigin(zTranformArray,D);
                ft = (sum1*C)/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==2){
                fmax = weirstrass(X5TranformArray,D);
                sum1 = weirstrass(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==3){
                fmax = griewank(X5TranformArray,D);
                sum1 = griewank(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==4){
                fmax = ackley(X5TranformArray,D);
                sum1 = ackley(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==5){
                fmax = sphere(X5TranformArray,D);
                sum1 = sphere(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==6){
                fmax = expandedSchaffer(X5TranformArray,D);
                sum1 = expandedSchaffer(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==7){
                fmax = expandedGriewankRosenbrock(X5TranformArray,D);
                sum1 = expandedGriewankRosenbrock(zTranformArray,D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==8){
                fmax = expandedSchaffer(round(X5TranformArray),D);
                sum1 = expandedSchaffer(round(zTranformArray),D);
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==9){
                fmax = rastrigin(round(X5TranformArray),D);
                sum1 = rastrigin(round(zTranformArray),D);
                ft = (sum1*C)/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==10){
                fmax = elliptic(X5TranformArray,D);
                sum1 = elliptic(zTranformArray,D);
                ft = (sum1*C)/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }

            else if(compositeFn.get(i)==11){
                fmax = sphere(X5TranformArray,D)*(1+(0.1*Math.random()));
                sum1 = sphere(zTranformArray,D)*(1+(0.1*Math.random()));
                ft = sum1*C/Math.abs(fmax);
                sum2 = sum2 +(w.get(i)*(ft+bias.get(i)));
            }


        }
        return sum2;
    }

    public ArrayList <Double> round(ArrayList <Double> zArray){
        ArrayList <Double> z = new ArrayList <Double>();
        int a;
        double b;
        double x;
        double x2;

        for(int i=0;i<D;i++){
            x = zArray.get(i) ;
            x2=x*2;
            if (x<0.5){
                z.add(x);
            }
            else{
                b = x2%1;
                a = (int) (x2-(b));
                if(x2<=0 && b>=0.5){
                    z.add(((double) (a-1))/2);
                }
                else if(b<0.5){
                    z.add(((double) (a))/2);
                }
                else{
                    z.add(((double) (a+1))/2);
                }
            }
        }
        return z;
    }

    public void setName(int f){
        if(f==1){ // spherical function
            this.fnName = "Sphere";
        }

        else if(f==2){ // Rosenbrock function
            this.fnName = "Rosenbrock";
        }

        else if(f==3){ // Ackley function
            this.fnName = "Ackley";
        }

        else if(f==4){ // Griewank function
            this.fnName = "Griewank";
        }

        else if(f==5){ // Rastrigin function
            this.fnName = "Rastrigin";
        }

        else if(f==6){ // Schaffer (2D) function
            this.fnName = "Schaffer2D";
        }

        else if(f==7){ // Griewank 10D function
            this.fnName = "Griewank10D";
        }

        else if(f==8){ // Shifted sphere function
            this.fnName = "ShiftedSphere";
        }

        else if(f==9){ // Shifted schwefel function
            this.fnName = "ShiftedSchwefel";
        }

        else if(f==10){ // Shifted rotated eliptical function
            this.fnName = "ShiftedRotatedEliptical";
        }

        else if(f==11){ // Shifted schwefel function with noise
            this.fnName = "ShiftedNoiseSchwefel";
        }

        else if(f==12){ // Shifted schwefel function with global optimum on bounds
            this.fnName = "OptimumOnBoundsSchwefel";
        }


        else if(f==13){ // Shifted rosenbrock function
            this.fnName = "ShiftedRosenbrock";
        }

        else if(f==14){ // Shifted rotated griewank function without bounds
            this.fnName = "OptimumOnBoundsRotatedGriewank";
        }

        else if(f==15){ // Shifted rotated Ackley function global optimum on bounds
            this.fnName = "OptimumOnBoundsRotatedAckley";
        }

        else if(f==16){ //Shifted Rastrigin
            this.fnName = "ShiftedRastrigin";
        }

        else if(f==17){ //Shifted & Rotated Rastrigin
            this.fnName = "ShiftedRotatedRastrigin";
        }

        else if(f==18){ //Shifted & Rotated Weirstrass
            this.fnName = "ShiftedRotatedWeirstrass";
        }

        else if(f==19){ // Schwefel
            this.fnName = "Schwefel";
        }

        else if(f==20){ // Shifted expanded  griewank plus rosenbrock function
            this.fnName = "ShiftedExpandedGriewankRosenbrock";
        }


        else if(f==21){ // Shifted rotated expanded  schaffer
            this.fnName = "ShiftedRotatedExpandedSchaffer";
        }


        else if(f==22){ // composition function
            this.fnName = "F15";
        }

        else if(f==23){ // composition function (rotated version of 22)
            this.fnName = "F16";
        }

        else if(f==24){ // composition function (noise added to 23)
            this.fnName = "F17";
        }

        else if(f==25){ // composition function
            this.fnName = "F18";
        }

        else if(f==26){ // composition function (diff parameters from 25)
            this.fnName = "F19";
        }

        else if(f==27){ // composition function (25 with optimum moved to boundary)
            this.fnName = "F20";
        }

        else if(f==28){ // composition function
            this.fnName = "F21";
        }

        else if(f==29){ // composition function
            this.fnName = "F22";
        }

        else if(f==30){ // composition function (non continuous 29)
            this.fnName = "F23";
        }

        else if(f==31){ // composition function
            this.fnName = "F24";
        }

        else if(f==32){ // composition function
            this.fnName = "F25";
        }
    }
}
