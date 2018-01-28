interface CommandInstruction {
        public abstract void execute ( );
}

class Fan {
        public void startRotate() {
                System.out.println("Ventilador en marcha");
        }
        public void stopRotate() {
                System.out.println("Ventilador parado");
        }
}
class Light {
        public void turnOn( ) {
                System.out.println("Luz encendida");
        }
        public void turnOff( ) {
                System.out.println("Light apagada");
        }
}
class Switch {
        private CommandInstruction UpCommand, DownCommand;
        public Switch( CommandInstruction Up, CommandInstruction Down) {
                UpCommand = Up; // concrete Command registers itself with the invoker 
                DownCommand = Down;
        }
        void flipUp( ) { // invoker calls back concrete Command, which executes the Command on the receiver 
                        UpCommand . execute ( ) ;                           
        }
        void flipDown( ) {
                        DownCommand . execute ( );
        }
}
class LightOnCommand implements CommandInstruction {
        private Light myLight;
        public LightOnCommand ( Light L) {
                myLight  =  L;
        }
        public void execute( ) {
                myLight . turnOn( );
        }
}
class LightOffCommand implements CommandInstruction {
        private Light myLight;
        public LightOffCommand ( Light L) {
                myLight  =  L;
        }
        public void execute( ) {
                myLight . turnOff( );
        }
}
class FanOnCommand implements CommandInstruction {
        private Fan myFan;
        public FanOnCommand ( Fan F) {
                myFan  =  F;
        }
        public void execute( ) {
                myFan . startRotate( );
        }
}
class FanOffCommand implements CommandInstruction {
        private Fan myFan;
        public FanOffCommand ( Fan F) {
                myFan  =  F;
        }
        public void execute( ) {
                myFan . stopRotate( );
        }
}
public class Command {
                public static void main(String[] args) {
                        Light  testLight = new Light( );
                        LightOnCommand testLOC = new LightOnCommand(testLight);
                        LightOffCommand testLFC = new LightOffCommand(testLight);
                        Switch testSwitch = new Switch( testLOC,testLFC);       
                        testSwitch.flipUp( );
                        testSwitch.flipDown( );
                        Fan testFan = new Fan( );
                        FanOnCommand foc = new FanOnCommand(testFan);
                        FanOffCommand ffc = new FanOffCommand(testFan);
                        Switch ts = new Switch( foc,ffc);
                        ts.flipUp( );
                        ts.flipDown( ); 
                }
}              