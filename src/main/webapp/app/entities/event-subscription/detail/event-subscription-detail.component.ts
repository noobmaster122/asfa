import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IEventSubscription } from '../event-subscription.model';

@Component({
  standalone: true,
  selector: 'jhi-event-subscription-detail',
  templateUrl: './event-subscription-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EventSubscriptionDetailComponent {
  eventSubscription = input<IEventSubscription | null>(null);

  previousState(): void {
    window.history.back();
  }
}
