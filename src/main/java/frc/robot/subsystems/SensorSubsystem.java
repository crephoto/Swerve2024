package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.Board.SensorTab;
import frc.robot.constants.SensorConstants;

public class SensorSubsystem extends SubsystemBase{
  private DigitalInput[] m_timeOfFlightSensors;
  private SensorTab m_sensorTab;
  public SensorSubsystem() {
    m_timeOfFlightSensors = new DigitalInput[SensorConstants.kDIOPorts]; // ten ports total
    m_sensorTab = SensorTab.getInstance();
    getSensorValue(1);
  }

  @Override
  public void periodic() {
    // Loop over every port and update them if they exist
    for(int index = 0; index < SensorConstants.kDIOPorts; index++){
      if(m_sensorTab.sensorStatePortExists(index)){
        m_sensorTab.setSensorStatePort(m_timeOfFlightSensors[index].get(), index);
      }
    }
  }
  /**
   * Returns the value at a port, if it does not exist, it is created
   * @param port The port to check 
  */
  public boolean getSensorValue(int port){
    if(!m_sensorTab.sensorStatePortExists(port)){
      createSensor(port);
    }
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Returns the value at a port, if it does not exist, it is *NOT* created
   * @param port The port to check 
  */
  public boolean getSensorValueUnsafe(int port){
    return m_sensorTab.getSensorStatePort(port);
  }
  /**
   * Creates a new Senor with DigitalInput(port); and posts it to the shuffleboard
   * This is not a safe method, improper usage can crash the robot so it is private
   * You need to make sure port does not already exist via sensorStatePortExists
   * @param port The port to make 
  */
  private void createSensor(int port){
    m_timeOfFlightSensors[port] = new DigitalInput(port);
    m_sensorTab.addSensorStatePort(port);
  }
}
