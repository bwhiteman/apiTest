import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { HotlistRefCode } from './hotlist-ref-code.model';
import { HotlistRefCodePopupService } from './hotlist-ref-code-popup.service';
import { HotlistRefCodeService } from './hotlist-ref-code.service';

@Component({
    selector: 'jhi-hotlist-ref-code-delete-dialog',
    templateUrl: './hotlist-ref-code-delete-dialog.component.html'
})
export class HotlistRefCodeDeleteDialogComponent {

    hotlistRefCode: HotlistRefCode;

    constructor(
        private hotlistRefCodeService: HotlistRefCodeService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hotlistRefCodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hotlistRefCodeListModification',
                content: 'Deleted an hotlistRefCode'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('apiTestApp.hotlistRefCode.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-hotlist-ref-code-delete-popup',
    template: ''
})
export class HotlistRefCodeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotlistRefCodePopupService: HotlistRefCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.hotlistRefCodePopupService
                .open(HotlistRefCodeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
