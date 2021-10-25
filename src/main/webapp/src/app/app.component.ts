import {Component} from '@angular/core';
import {TestControllerWccApi} from "../generated/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Workflow Control Center';
  currentTime = '';

  constructor(private api: TestControllerWccApi) {
  }

  async getCurrentTime() {
    this.currentTime = 'loading...';
    await this.api
              .getCurrentServerTime()
              .toPromise()
              .then(r => this.currentTime = JSON.stringify(r));
  }
}
