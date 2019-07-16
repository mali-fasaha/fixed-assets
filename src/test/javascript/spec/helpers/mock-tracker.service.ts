import { SpyObject } from './spyobject';
import { GhaTrackerService } from 'app/core/tracker/tracker.service';

export class MockTrackerService extends SpyObject {
  constructor() {
    super(GhaTrackerService);
  }

  connect() {}
}
