// Generated by view binder compiler. Do not edit!
package edu.lehigh.cse216.law322.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import edu.lehigh.cse216.law322.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentFirst2Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonCancel;

  @NonNull
  public final Button buttonOk;

  @NonNull
  public final EditText editText;

  @NonNull
  public final TextView specialMessage;

  private FragmentFirst2Binding(@NonNull ConstraintLayout rootView, @NonNull Button buttonCancel,
      @NonNull Button buttonOk, @NonNull EditText editText, @NonNull TextView specialMessage) {
    this.rootView = rootView;
    this.buttonCancel = buttonCancel;
    this.buttonOk = buttonOk;
    this.editText = editText;
    this.specialMessage = specialMessage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFirst2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFirst2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_first2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFirst2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonCancel;
      Button buttonCancel = ViewBindings.findChildViewById(rootView, id);
      if (buttonCancel == null) {
        break missingId;
      }

      id = R.id.buttonOk;
      Button buttonOk = ViewBindings.findChildViewById(rootView, id);
      if (buttonOk == null) {
        break missingId;
      }

      id = R.id.editText;
      EditText editText = ViewBindings.findChildViewById(rootView, id);
      if (editText == null) {
        break missingId;
      }

      id = R.id.specialMessage;
      TextView specialMessage = ViewBindings.findChildViewById(rootView, id);
      if (specialMessage == null) {
        break missingId;
      }

      return new FragmentFirst2Binding((ConstraintLayout) rootView, buttonCancel, buttonOk,
          editText, specialMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
