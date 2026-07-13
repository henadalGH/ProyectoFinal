import { TestBed } from '@angular/core/testing';

import { BalanceServicio } from './balance-servicio';

describe('BalanceServicio', () => {
  let service: BalanceServicio;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BalanceServicio);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
