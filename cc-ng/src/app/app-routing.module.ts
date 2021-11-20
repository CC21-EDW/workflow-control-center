import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LogComponent} from "./log/log.component";
import {CidComponent} from "./cid/cid.component";
import {SystemOverviewComponent} from "./system-overview/system-overview.component";

const routes: Routes = [
  {path: 'log', component: LogComponent},
  {path: 'cid', component: CidComponent},
  {path: 'system-overview', component: SystemOverviewComponent},
  {path: '**', redirectTo: 'system-overview'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
