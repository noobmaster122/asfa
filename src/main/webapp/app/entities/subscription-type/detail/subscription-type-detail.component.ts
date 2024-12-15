import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISubscriptionType } from '../subscription-type.model';

@Component({
  standalone: true,
  selector: 'jhi-subscription-type-detail',
  templateUrl: './subscription-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SubscriptionTypeDetailComponent {
  subscriptionType = input<ISubscriptionType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
