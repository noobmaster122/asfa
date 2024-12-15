import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMember } from '../member.model';
import { MemberService } from '../service/member.service';

@Component({
  standalone: true,
  templateUrl: './member-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MemberDeleteDialogComponent {
  member?: IMember;

  protected memberService = inject(MemberService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.memberService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
