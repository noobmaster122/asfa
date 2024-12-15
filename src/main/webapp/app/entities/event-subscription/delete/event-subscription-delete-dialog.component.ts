import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEventSubscription } from '../event-subscription.model';
import { EventSubscriptionService } from '../service/event-subscription.service';

@Component({
  standalone: true,
  templateUrl: './event-subscription-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EventSubscriptionDeleteDialogComponent {
  eventSubscription?: IEventSubscription;

  protected eventSubscriptionService = inject(EventSubscriptionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventSubscriptionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
