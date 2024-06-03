import {Component, OnInit} from '@angular/core';
import { WebSocketAPI } from '../api/WebSocketAPI';
import { HttpClient } from "@angular/common/http";
import { BleMeasurement, Reporter } from '../dto/BleMeasurement';
import { MeasurementResult } from '../dto/MeasurementResult';
import { lastValueFrom } from 'rxjs';

const sleep = (ms: number) => new Promise(r => setTimeout(r, ms));

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

  webSocketAPI!: WebSocketAPI;
  measurementArr1 : BleMeasurement [] | undefined;
  measurementArr2 : BleMeasurement [] | undefined;
  measurement: BleMeasurement | undefined;
  measurementResult : MeasurementResult | undefined;
  measurementResultArr : MeasurementResult [] | undefined;
  

  constructor(private httpClient: HttpClient) {
    this.measurementArr1 = [];
    this.measurementArr2 = [];
    this.measurementResultArr = [];
  }

  ngOnInit() {
    this.webSocketAPI = new WebSocketAPI(this); 

    this.webSocketAPI.data$.subscribe((resultResponse : MeasurementResult) => {
      
      //Implementing Live Update by updating Avgs in place if device address already exists and adding it if it doesn't.
      const found = this.measurementResultArr?.some(e => e.deviceAddress === resultResponse.deviceAddress);
      
      if(!found){
        this.measurementResultArr?.push(resultResponse)
      }
      else{
        this.measurementResultArr?.forEach(measurement => {
          if(measurement.deviceAddress === resultResponse.deviceAddress){
            measurement.avgHumidity = resultResponse.avgHumidity;
            measurement.avgTemperature = resultResponse.avgTemperature;
          }
        });
      }
    });
}
    
  connect() { 
    this.webSocketAPI._connect();
  }

  disconnect() {
    this.webSocketAPI._disconnect();
  }

 async sendMessage() {
    //reading measurements into an array of objects and waiting until it's all read.
    this.measurementArr1 = await lastValueFrom(this.httpClient.get<BleMeasurement[]>("assets/part1.json"));

    //Sending every object in the array.
    this.measurementArr1?.forEach(measurement => {
      this.webSocketAPI._send(JSON.stringify(measurement));
    });

    //simulating 10 seconds wait
    await sleep(10000);

    this.measurementArr2 = await lastValueFrom(this.httpClient.get<BleMeasurement[]>("assets/part2.json"));

    this.measurementArr2?.forEach(measurement => {
    this.webSocketAPI._send(JSON.stringify(measurement));
  });

  await sleep(10000);

  this.measurementArr1 = await lastValueFrom(this.httpClient.get<BleMeasurement[]>("assets/part1.json"));

  this.measurementArr1?.forEach(measurement => {
  this.webSocketAPI._send(JSON.stringify(measurement));
  });

  }
 }

