import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { AppComponent } from '../app/app.component';
import {Observable, Subject} from "rxjs";

export class WebSocketAPI {
    webSocketEndPoint = 'http://localhost:8080/ws';
    topic = '/topic/test';
    ws: any;
    stompClient: any;
    appComponent: AppComponent;
    private _data$: Subject<any>;
    // messageReceived: any;

    constructor(appComponent: AppComponent) {
        this.appComponent = appComponent;
        this._data$ = new Subject<any>();
    }

    public get data$(): Observable<any> {
      return this._data$.asObservable();
    }

    _connect() {
        console.log('Initialize WebSocket Connection');
        this.ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(<WebSocket>this.ws);
      // tslint:disable-next-line:variable-name
        const _this = this;
      // tslint:disable-next-line:only-arrow-functions
        _this.stompClient.connect({}, function() {
          // tslint:disable-next-line:only-arrow-functions
            _this.stompClient.subscribe(_this.topic, function(sdkEvent: any) {
                _this.onMessageReceived(sdkEvent);
            });
        }, this.errorCallBack);
    }

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log('Disconnected');
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error: any) {
        console.log('errorCallBack -> ' + error);
        setTimeout(() => {
            this._connect();
        }, 5000);
    }


	/**
	 * Send message to sever via web socket
	 * @param {*} message 
	 */
    
    _send(message: any) { 
      this.stompClient.send('/app/measurements', {}, message);
    }

    onMessageReceived(message: any) {
      console.log('Message Recieved from Server :: ' + message);
      this._data$.next(JSON.parse(message.body));
    }

}
