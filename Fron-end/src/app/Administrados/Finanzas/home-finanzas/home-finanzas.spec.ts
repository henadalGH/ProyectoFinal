import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeFinanzas } from './home-finanzas';

describe('HomeFinanzas', () => {
  let component: HomeFinanzas;
  let fixture: ComponentFixture<HomeFinanzas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeFinanzas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeFinanzas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
