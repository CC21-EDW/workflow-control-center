import {Component, OnInit} from '@angular/core';
import {EdwControllerWccApi, StatusDto} from "../../generated/api";
import {BalToastService} from "@baloise/design-system-components-angular";

@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.scss']
})
export class LogComponent implements OnInit {
  logs?: StatusDto[];

  constructor(private api: EdwControllerWccApi,
              private toaster: BalToastService
              ) {
  }

  async ngOnInit(): Promise<void> {
    try {
      this.logs = await this.api.getEdwStatus().toPromise();
    } catch (e) {
      this.toaster.create({ message: 'Requsting data from Kafka failed!', color: 'danger' })
    }
  }
}
