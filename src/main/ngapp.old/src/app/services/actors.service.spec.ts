import { TestBed } from '@angular/core/testing';

import { ActorsService } from './actors.service';

describe('ActorsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ActorsService = TestBed.get(ActorsService);
    expect(service).toBeTruthy();
  });
});
