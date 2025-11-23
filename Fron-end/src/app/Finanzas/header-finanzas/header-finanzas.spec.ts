import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderFinanzas } from './header-finanzas';

describe('HeaderFinanzas', () => {
  let component: HeaderFinanzas;
  let fixture: ComponentFixture<HeaderFinanzas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderFinanzas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderFinanzas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
