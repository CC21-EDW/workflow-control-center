import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ApiModule} from "../generated/api";
import {BaloiseDesignSystemModule} from "@baloise/design-system-components-angular";
import {HttpClientModule} from "@angular/common/http";
import {LogComponent} from './log/log.component';
import {CidComponent} from './cid/cid.component';
import {SystemOverviewComponent} from './system-overview/system-overview.component';

@NgModule({
  declarations: [
    AppComponent,
    LogComponent,
    CidComponent,
    SystemOverviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BaloiseDesignSystemModule.forRoot(),
    ApiModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
