import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CidComponent } from './cid.component';

describe('CidComponent', () => {
  let component: CidComponent;
  let fixture: ComponentFixture<CidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CidComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
