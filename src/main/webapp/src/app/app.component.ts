import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Workflow Control Center';
  currentTime = '';

  constructor(private http: HttpClient) {
  }

  async getCurrentTime() {
    this.currentTime = 'loading...';
    await this.http
              .get(`/api/get-time`)
              .toPromise()
              .then(r => this.currentTime = JSON.stringify(r));
  }
}
