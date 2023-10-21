import { TestBed } from '@angular/core/testing';

import { StubTokenService } from './stub-token.service';

describe('StubTokenService', () => {
  let service: StubTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StubTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
