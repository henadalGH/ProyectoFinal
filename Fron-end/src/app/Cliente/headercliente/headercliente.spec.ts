import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Headercliente } from './headercliente';

describe('Headercliente', () => {
  let component: Headercliente;
  let fixture: ComponentFixture<Headercliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Headercliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Headercliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
